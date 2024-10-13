package com.dogadopt.dog_adopt.service.adoption;

import com.dogadopt.dog_adopt.dto.incoming.DogAndUserAdoptionCreateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AdoptionInfo;
import jakarta.validation.Valid;

import java.util.Map;

public interface AdoptionService {

    AdoptionInfo adopt(Long userId, Long dogId, @Valid DogAndUserAdoptionCreateCommand command);

    void deleteAdoptionByUser(Long adoptionId);

    Map<String, String> updateAdoption(Long adoptionId, Map<String, Object> updates);

    void deleteAdoptionByAdmin(Long adoptionId);
}
