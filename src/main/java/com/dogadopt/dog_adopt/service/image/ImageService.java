package com.dogadopt.dog_adopt.service.image;

import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.Image;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {


    List<Image> uploadFile(List<MultipartFile> images, String folder, Long id, ImageType imageType);


    List<Image> getFirstImagesForAllDogs(ImageType imageType); //Ha nem lesz használva törölni

    Image getImagesForShelter(Shelter actualShelter);

    Image getFirstImageOfDog(Dog actualDog);

    List<Image> getAllImagesForOneDog(Dog dog);

    void deleteImage(Long pictureId, String folder);
}
