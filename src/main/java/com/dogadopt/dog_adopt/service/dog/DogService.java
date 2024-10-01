package com.dogadopt.dog_adopt.service.dog;


import com.dogadopt.dog_adopt.dto.incoming.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import jakarta.validation.Valid;

public interface DogService {
    DogInfoOneDog registerDog(@Valid DogCreateUpdateCommand command);
}
