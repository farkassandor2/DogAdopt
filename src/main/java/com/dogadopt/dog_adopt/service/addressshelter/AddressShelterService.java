package com.dogadopt.dog_adopt.service.addressshelter;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.domain.AddressShelter;
import com.dogadopt.dog_adopt.domain.Shelter;

public interface AddressShelterService {

    void save(AddressShelter addressShelter);

    void deleteConnectionBetweenShelterAndAddress(Shelter shelter, Address address);
}
