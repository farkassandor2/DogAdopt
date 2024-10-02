package com.dogadopt.dog_adopt.service.address;

import com.dogadopt.dog_adopt.dto.incoming.CreateUpdateAddressCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AddressInfo;

public interface AddressService {

    AddressInfo registerAddress(CreateUpdateAddressCommand addressCommand);
}
