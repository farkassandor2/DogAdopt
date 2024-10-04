package com.dogadopt.dog_adopt.service.address;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.dto.incoming.AddressCreateUpdateCommand;
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

    public AddressInfo getAddressInfo(AddressCreateUpdateCommand command) {
        Address address = registerAddress(command);
        return modelMapper.map(address, AddressInfo.class);
    }


    @Override
    public Address registerAddress(AddressCreateUpdateCommand addressCommand) {
        Address address = null;

        if (addressCommand != null) {
            String zip = addressCommand.getZip();
            boolean isValidZip = zipcodeService.validate(zip, addressCommand.getCountry());

            if (isValidZip) {
                address = getAddressIfExists(addressCommand);

                if (address == null) {
                    address = modelMapper.map(addressCommand, Address.class);
                    addressRepository.save(address);
                }
            } else {
                throw new ZipInvalidException("This zipcode is invalid int the specified country!");
            }
        }
        return address;
    }

    @Override
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> getAddressesForShelter(Shelter actualShelter) {
        return addressRepository.getAddressesForShelter(actualShelter.getId());
    }

    private Address getAddressIfExists(AddressCreateUpdateCommand command) {
        return addressRepository.findByAllArgs(command.getZip(),
                                                          command.getCountry(),
                                                          command.getCity(),
                                                          command.getStreet(),
                                                          command.getHouseNumber());
    }
}
