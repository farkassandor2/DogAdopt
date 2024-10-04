package com.dogadopt.dog_adopt.service.address;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.dto.incoming.AddressCreateUpdateCommand;

import java.util.List;

public interface AddressService {

    Address registerAddress(AddressCreateUpdateCommand addressCommand);

    List<Address> getAddresses();

    List<Address> getAddressesForShelter(Shelter actualShelter);
}
