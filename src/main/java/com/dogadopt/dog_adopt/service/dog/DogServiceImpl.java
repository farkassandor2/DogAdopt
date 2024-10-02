package com.dogadopt.dog_adopt.service.dog;

import com.dogadopt.dog_adopt.config.ObjectMapperUtil;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.Image;
import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import com.dogadopt.dog_adopt.dto.incoming.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoListOfDogs;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import com.dogadopt.dog_adopt.repository.DogRepository;
import com.dogadopt.dog_adopt.service.image.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DogServiceImpl implements DogService{

    private static final String DOG_FOLDER = "dogs";
    private static final ImageType DOG_IMAGE = ImageType.DOG;

    private final DogRepository dogRepository;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Override
    public DogInfoOneDog registerDog(DogCreateUpdateCommand command) {

        Dog dog = modelMapper.map(command, Dog.class);
        dogRepository.save(dog);

        List<MultipartFile> multipartFiles = command.getImages();
        setImageToDog(multipartFiles, dog);

        List<String> imgUrls = dog.getImages().stream()
                .map(Image::getUrl)
                .toList();

        DogInfoOneDog info = modelMapper.map(dog, DogInfoOneDog.class);
        info.setImageUrls(imgUrls);

        return info;
    }

    @Override
    public List<DogInfoListOfDogs> listAllDogs() {

        List<Dog> dogs = dogRepository.findAll();
        List<Image> firstImageOfDogs = imageService.getFirstImageOfDogs();
        List<DogInfoListOfDogs> dogInfos = ObjectMapperUtil.mapAll(dogs, DogInfoListOfDogs.class);

        for (int i = 0; i < dogs.size(); i++) {
            for (Image firstImageOfDog : firstImageOfDogs) {
                if (dogs.get(i) == firstImageOfDog.getDog()) {
                    dogInfos.get(i).setImgUrl(firstImageOfDog.getUrl());
                }
            }
        }
        return dogInfos;
    }

    private void setImageToDog(List<MultipartFile> multipartFiles, Dog dog) {
        if (!multipartFiles.isEmpty()) {
            List<Image> images = imageService.uploadFile(multipartFiles, DOG_FOLDER, dog.getId(), DOG_IMAGE);
            dog.setImages(images);
            images.forEach(i -> i.setDog(dog));
        }
    }
}
