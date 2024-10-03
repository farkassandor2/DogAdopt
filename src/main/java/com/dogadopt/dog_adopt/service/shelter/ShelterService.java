package com.dogadopt.dog_adopt.service.shelter;

import com.dogadopt.dog_adopt.dto.incoming.ShelterCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterInfo;
import jakarta.validation.Valid;

import java.util.List;

public interface ShelterService {

    ShelterInfo registerShelter(@Valid ShelterCreateUpdateCommand command);

    List<ShelterInfo> listAllShelters();
}
