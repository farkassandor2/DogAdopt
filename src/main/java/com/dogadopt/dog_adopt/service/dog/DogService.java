package com.dogadopt.dog_adopt.service.dog;


import com.dogadopt.dog_adopt.dto.incoming.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfo;
import jakarta.validation.Valid;

public interface DogService {
    DogInfo registerDog(@Valid DogCreateUpdateCommand command);
}
