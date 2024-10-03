package com.dogadopt.dog_adopt.service.address;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.dto.incoming.CreateUpdateAddressCommand;

public interface AddressService {

    Address registerAddress(CreateUpdateAddressCommand addressCommand);

}
