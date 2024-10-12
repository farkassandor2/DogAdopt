package com.dogadopt.dog_adopt.service.doganduseradoption;

import com.dogadopt.dog_adopt.dto.incoming.DogAndUserAdoptionCreateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogAndUserAdoptionInfo;
import jakarta.validation.Valid;

public interface DogAndUserAdoptionService {

    DogAndUserAdoptionInfo adopt(Long userId, Long dogId, @Valid DogAndUserAdoptionCreateCommand command);
}
