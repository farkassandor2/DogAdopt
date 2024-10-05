package com.dogadopt.dog_adopt.service.address;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.dto.incoming.AddressCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AddressInfo;
import jakarta.validation.Valid;

import java.util.List;

public interface AddressService {

    Address registerAddress(AddressCreateUpdateCommand addressCommand);

    List<Address> getAddresses();

    List<Address> getAddressesForShelter(Shelter actualShelter);

    void save(Address newAddress);

    Address getAddressById(Long addressId);
}
