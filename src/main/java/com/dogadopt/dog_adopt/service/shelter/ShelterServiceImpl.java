package com.dogadopt.dog_adopt.service.shelter;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.domain.enums.address.Country;
import com.dogadopt.dog_adopt.dto.incoming.CreateUpdateAddressCommand;
import com.dogadopt.dog_adopt.dto.incoming.ShelterCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AddressInfo;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterInfo;
import com.dogadopt.dog_adopt.repository.ShelterRepository;
import com.dogadopt.dog_adopt.service.address.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ShelterServiceImpl implements ShelterService{

    private final ShelterRepository shelterRepository;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Override
    public ShelterInfo registerShelter(ShelterCreateUpdateCommand command) {

        Shelter shelter = modelMapper.map(command, Shelter.class);

        CreateUpdateAddressCommand addressCommand = transformShelterCommandToAddressCommand(command);
        Address address = addressService.registerAddress(addressCommand);

        shelter.setAddresses(new ArrayList<>(List.of(address)));
        address.setShelter(shelter);
        shelterRepository.save(shelter);

        AddressInfo addressInfo = modelMapper.map(address, AddressInfo.class);

        ShelterInfo shelterInfo = modelMapper.map(shelter, ShelterInfo.class);
        shelterInfo.setAddressInfos(new ArrayList<>(List.of(addressInfo)));
        return shelterInfo;
    }

    private CreateUpdateAddressCommand transformShelterCommandToAddressCommand(ShelterCreateUpdateCommand command) {
        String zip = command.getAddressInfo().getZip();
        Country country = command.getAddressInfo().getCountry();
        String city = command.getAddressInfo().getCity();
        String street = command.getAddressInfo().getStreet();
        String houseNumber = command.getAddressInfo().getHouseNumber();

        return new CreateUpdateAddressCommand(zip, country, city, street, houseNumber);
    }
}
