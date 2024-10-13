package com.dogadopt.dog_adopt.service.dog;


import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.dto.incoming.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.incoming.ImageUploadCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoListOfDogs;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;


public interface DogService {

    DogInfoOneDog registerDog(@Valid DogCreateUpdateCommand command);

    List<DogInfoListOfDogs> listAllDogs();

    DogInfoOneDog getOneDogInfo(Long dogId);

    List<DogInfoListOfDogs> getAllDogsFromShelter(Long shelterId);

    Dog getOneDog(Long id);

    DogInfoOneDog updateDog(Long dogId, @Valid Map<String, Object> updates);

    void updateDogAge();

    void uploadPictureForDog(Long dogId, ImageUploadCommand command);

    void deletePictureForDog(Long pictureId);

    List<Dog> getFavoriteDogsOfUser(AppUser user);

    List<Dog> getAdoptedDogsOfUser(AppUser user);
}
