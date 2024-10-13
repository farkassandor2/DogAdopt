package com.dogadopt.dog_adopt.service.doganduseradoption;

import com.dogadopt.dog_adopt.dto.incoming.DogAndUserAdoptionCreateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogAndUserAdoptionInfo;
import jakarta.validation.Valid;

import java.util.Map;

public interface AdoptionService {

    DogAndUserAdoptionInfo adopt(Long userId, Long dogId, @Valid DogAndUserAdoptionCreateCommand command);

    void deleteAdoptionByUser(Long adoptionId);

    Map<String, String> updateAdoption(Long adoptionId, Map<String, Object> updates);

    void deleteAdoptionByAdmin(Long adoptionId);
}
