package com.dogadopt.dog_adopt.service.dog;


import com.dogadopt.dog_adopt.dto.incoming.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoListOfDogs;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import jakarta.validation.Valid;

import java.util.List;


public interface DogService {
    DogInfoOneDog registerDog(@Valid DogCreateUpdateCommand command);


    List<DogInfoListOfDogs> listAllDogs();
}
