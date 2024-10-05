package com.dogadopt.dog_adopt.service.shelter;

import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.dto.incoming.AddressCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.incoming.ShelterCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AddressInfo;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoListOfDogs;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterDTOForDropDownMenu;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterInfoForUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public interface ShelterService {

    ShelterInfoForUser registerShelter(@Valid ShelterCreateUpdateCommand command);

    List<ShelterInfoForUser> listAllShelters();

    List<ShelterDTOForDropDownMenu> getSheltersListForDropDown();

    Shelter getShelter(@NotNull Long shelterId);

    ShelterInfoForUser updateShelter(Long shelterId, @Valid Map<String, Object> updates);

    List<AddressInfo> addNewAddress(Long shelterId, @Valid AddressCreateUpdateCommand command);

    void deleteConnectionBetweenShelterAndAddress(Long shelterId, Long addressId);
}
