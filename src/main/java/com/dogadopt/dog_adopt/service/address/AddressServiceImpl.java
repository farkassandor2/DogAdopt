package com.dogadopt.dog_adopt.service.address;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.dto.incoming.CreateUpdateAddressCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AddressInfo;
import com.dogadopt.dog_adopt.exception.ZipInvalidException;
import com.dogadopt.dog_adopt.repository.AddressRepository;
import com.dogadopt.dog_adopt.service.zip.ZipcodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final ZipcodeService zipcodeService;
    private final ModelMapper modelMapper;

    public AddressInfo getAddressInfo(CreateUpdateAddressCommand command) {
        Address address = registerAddress(command);
        return modelMapper.map(address, AddressInfo.class);
    }


    @Override
    public Address registerAddress(CreateUpdateAddressCommand addressCommand) {
        if (addressCommand != null) {
            String zip = addressCommand.getZip();
            boolean isValidZip = zipcodeService.validate(zip, addressCommand.getCountry());

            if (isValidZip) {
                Address address = modelMapper.map(addressCommand, Address.class);
                addressRepository.save(address);
                return address;
            } else {
                throw new ZipInvalidException("This zipcode is invalid int the specified country!");
            }
        }
        return new Address();
    }

    @Override
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }
}
