package com.dogadopt.dog_adopt.service.addressshelter;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.domain.AddressShelter;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.repository.AddressShelterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressShelterServiceImpl implements AddressShelterService {

    private final AddressShelterRepository addressShelterRepository;

    @Override
    public void save(AddressShelter addressShelter) {
        addressShelterRepository.save(addressShelter);
    }

    @Override
    public void deleteConnectionBetweenShelterAndAddress(Shelter shelter, Address address) {
        addressShelterRepository.deleteConnectionBetweenShelterAndAddress(shelter, address);
    }
}
