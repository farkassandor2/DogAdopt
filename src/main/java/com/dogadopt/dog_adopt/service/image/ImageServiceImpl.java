package com.dogadopt.dog_adopt.service.image;

import com.cloudinary.Cloudinary;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.Image;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import com.dogadopt.dog_adopt.exception.CloudinaryException;
import com.dogadopt.dog_adopt.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    boolean isFirstPicture = false;

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;

    @Override
    public List<Image> uploadFile(List<MultipartFile> imageFiles, String folder, Long id, ImageType imageType) {

        Map<String, String> uploadOptions = new HashMap<>();
        List<Image> images = new ArrayList<>();
        AtomicReference<Map> uploadResult = new AtomicReference<>();

        if (imageFiles == null) {
            return images;
        }

        imageFiles.forEach(file -> {
            if (file != null && !file.isEmpty()) {
                try {

                    uploadOptions.put("folder", folder);

                     String newId = createNewId(id, uploadOptions, imageType);
                     checkIfFirstPicture(newId);

                    uploadOptions.put("public_id",newId);

                    uploadResult.set(cloudinary.uploader().upload(file.getBytes(), uploadOptions));

                } catch (IOException e) {
                    throw new CloudinaryException("Error uploading file");
                }
                String imageUrl = (String) uploadResult.get().get("secure_url");
                images.add(new Image(imageUrl, imageType, file.getOriginalFilename(), isFirstPicture));
            }
        });

        imageRepository.saveAll(images);
        return images;
    }

    @Override
    public List<Image> getFirstImagesForAllDogs(ImageType imageType) {      //Ha nem lesz használva törölni
        return imageRepository.getFirstImage(imageType);
    }

    @Override
    public Image getImagesForShelter(Shelter actualShelter) {
        return imageRepository.getImageForShelter(actualShelter);
    }

    @Override
    public Image getFirstImageOfDog(Dog actualDog) {
        return imageRepository.getFirstImageOfDog(actualDog);
    }

    @Override
    public List<String> getAllImagesForOneDog(Dog dog) {
        return imageRepository.getAllImagesForOneDog(dog);
    }

    @Override
    public void deleteImage(Shelter shelter, Long imgId) {
        imageRepository.deleteImage(shelter, imgId);
    }

    private void checkIfFirstPicture(String newId) {
        isFirstPicture = false;
        String[] idPerCharacter = newId.split("/");
        String secondHalf = idPerCharacter[1];

        int secondHalfInt = Integer.parseInt(secondHalf);
        if (secondHalfInt == 1) {
            isFirstPicture = true;
        }
    }

    private String createNewId(Long id, Map<String, String> uploadOptions, ImageType imageType) {

        String resultString;
        int secondHalfOfIdNew = 0;

        if (imageType == ImageType.DOG) {
            String firstHalfOfId = String.valueOf(id);
            int numberOfPicsOfDog = imageRepository.getNumberOfPicsUploadedToDog(id);

            if(numberOfPicsOfDog != 0) {
                secondHalfOfIdNew = numberOfPicsOfDog + 1;
            } else {
                String existingId = uploadOptions.getOrDefault("public_id", "0/0");
                String[] idPerCharacter = existingId.split("/");
                String secondHalfOfIdString = idPerCharacter[1];
                secondHalfOfIdNew = Integer.parseInt(secondHalfOfIdString) + 1;
            }

            String secondHalfOfIdNewString = String.valueOf(secondHalfOfIdNew);
            resultString = firstHalfOfId + "/" + secondHalfOfIdNewString;

        } else {
            resultString = id + "/1";
        }

        return resultString;
    }
}
