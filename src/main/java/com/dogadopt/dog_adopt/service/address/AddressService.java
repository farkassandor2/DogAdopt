package com.dogadopt.dog_adopt.service.address;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.dto.incoming.CreateUpdateAddressCommand;

import java.util.List;

public interface AddressService {

    Address registerAddress(CreateUpdateAddressCommand addressCommand);

    List<Address> getAddresses();
}
