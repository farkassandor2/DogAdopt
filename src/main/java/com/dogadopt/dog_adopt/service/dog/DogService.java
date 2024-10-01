package com.dogadopt.dog_adopt.service.dog;


import com.dogadopt.dog_adopt.dto.dog.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.dog.DogInfo;
import jakarta.validation.Valid;

public interface DogService {
    DogInfo registerDog(@Valid DogCreateUpdateCommand command);
}
