package com.dogadopt.dog_adopt.service.dog;

import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.Image;
import com.dogadopt.dog_adopt.dto.incoming.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import com.dogadopt.dog_adopt.repository.DogRepository;
import com.dogadopt.dog_adopt.service.image.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DogServiceImpl implements DogService{

    private final DogRepository dogRepository;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Override
    public DogInfoOneDog registerDog(DogCreateUpdateCommand command) {

        Dog dog = modelMapper.map(command, Dog.class);

        List<MultipartFile> images = command.getImages();
        imageService.uploadFile(images);

        dogRepository.save(dog);

        List<String> imgUrls = dog.getImages().stream()
                .map(Image::getUrl)
                .toList();

        DogInfoOneDog info = modelMapper.map(dog, DogInfoOneDog.class);

        info.setImageUrls(imgUrls);
        return info;
    }
}
