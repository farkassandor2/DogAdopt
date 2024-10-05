package com.dogadopt.dog_adopt.service.dog;

import com.dogadopt.dog_adopt.config.ObjectMapperUtil;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.Image;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.domain.enums.dog.DonationGoal;
import com.dogadopt.dog_adopt.domain.enums.dog.HealthStatus;
import com.dogadopt.dog_adopt.domain.enums.dog.Status;
import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import com.dogadopt.dog_adopt.dto.incoming.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoListOfDogs;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import com.dogadopt.dog_adopt.exception.DogNotFoundException;
import com.dogadopt.dog_adopt.repository.DogRepository;
import com.dogadopt.dog_adopt.service.image.ImageService;
import com.dogadopt.dog_adopt.service.shelter.ShelterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@Transactional
@RequiredArgsConstructor
public class DogServiceImpl implements DogService{

    private static final String DOG_FOLDER = "dogs";
    private static final ImageType DOG_IMAGE = ImageType.DOG;

    private final DogRepository dogRepository;
    private final ImageService imageService;
    private final ShelterService shelterService;
    private final ModelMapper modelMapper;

    @Override
    public DogInfoOneDog registerDog(DogCreateUpdateCommand command) {

        Dog dog = modelMapper.map(command, Dog.class);
        dogRepository.save(dog);

        Shelter shelter = shelterService.getShelter(command.getShelterId());
        dog.setShelter(shelter);

        List<MultipartFile> multipartFiles = command.getImages();
        saveImagesOfDog(multipartFiles, dog);

        List<String> imgUrls = dog.getImages().stream()
                .map(Image::getUrl)
                .toList();

        DogInfoOneDog info = modelMapper.map(dog, DogInfoOneDog.class);
        info.setImageUrls(imgUrls);
        info.setShelterId(command.getShelterId());

        return info;
    }

    @Override
    public List<DogInfoListOfDogs> listAllDogs() {

        List<Dog> dogs = dogRepository.findAll();
        List<DogInfoListOfDogs> dogInfos = ObjectMapperUtil.mapAll(dogs, DogInfoListOfDogs.class);

        setImgUrlAndShelterIdToDogInfo(dogs, dogInfos);
        return dogInfos;
    }

    @Override
    public DogInfoOneDog getOneDogInfo(Long dogId) {
        Dog dog = getOneDog(dogId);

        DogInfoOneDog info = modelMapper.map(dog, DogInfoOneDog.class);

        List<String> imgUrls = imageService.getAllImagesForOneDog(dog);
        info.setImageUrls(imgUrls);
        info.setShelterId(dog.getShelter().getId());

        return info;
    }

    @Override
    public List<DogInfoListOfDogs> getAllDogsFromShelter(Long shelterId) {
        List<Dog> dogsInShelter = dogRepository.getAllDogsFromShelter(shelterId);
        List<DogInfoListOfDogs> dogInfos = ObjectMapperUtil.mapAll(dogsInShelter, DogInfoListOfDogs.class);
        setImgUrlAndShelterIdToDogInfo(dogsInShelter, dogInfos);
        return dogInfos;
    }

    @Override
    public Dog getOneDog(Long dogId) {
        return dogRepository.findById(dogId)
                .orElseThrow(() -> new DogNotFoundException("Dog not found with ID: " + dogId));
    }

    @Override
    public DogInfoOneDog updateDog(Long dogId, Map<String, Object> updates) {
        Dog dog = getOneDog(dogId);

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    dog.setName((String) value);
                    break;
                case "healthStatus":
                    try {
                        dog.setHealthStatus(HealthStatus.valueOf(key));
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(BAD_REQUEST, "Invalid breed" + value);
                    }
                    break;
                case "status":
                    try {
                        dog.setStatus(Status.valueOf(key));
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(BAD_REQUEST, "Invalid status" + value);
                    }
                    break;
                case "donationGoal":
                    try {
                        dog.setDonationGoal(DonationGoal.valueOf(key));
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(BAD_REQUEST, "Invalid donation goal" + value);
                    }
                    break;
                default:
                    throw new ResponseStatusException(BAD_REQUEST, "Unknown field: " + key);
            }
        });
        dogRepository.save(dog);
        return modelMapper.map(dog, DogInfoOneDog.class);
    }

    private void setImgUrlAndShelterIdToDogInfo(List<Dog> dogs, List<DogInfoListOfDogs> dogInfos) {
        for (int i = 0; i < dogs.size(); i++) {
            Dog actualDog = dogs.get(i);
            Image imageOfActualDog = imageService.getFirstImageOfDog(actualDog);
            dogInfos.get(i).setImgUrl(imageOfActualDog.getUrl());
            dogInfos.get(i).setShelterId(dogs.get(i).getShelter().getId());
        }
    }

    private void saveImagesOfDog(List<MultipartFile> multipartFiles, Dog dog) {
        if (!multipartFiles.isEmpty()) {
            List<Image> images = imageService.uploadFile(multipartFiles, DOG_FOLDER, dog.getId(), DOG_IMAGE);
            setDogToImageAndImageToDog(dog, images);
        }
    }

    private void setDogToImageAndImageToDog(Dog dog, List<Image> images) {
        dog.setImages(images);
        images.forEach(i -> i.setDog(dog));
    }
}
