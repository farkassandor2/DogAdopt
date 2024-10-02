package com.dogadopt.dog_adopt.service.address;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.dto.incoming.CreateUpdateAddressCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AddressInfo;
import com.dogadopt.dog_adopt.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;


    @Override
    public AddressInfo registerAddress(CreateUpdateAddressCommand addressCommand) {
        Address address = modelMapper.map(addressCommand, Address.class);
        addressRepository.save(address);
        return modelMapper.map(address, AddressInfo.class);
    }
}
