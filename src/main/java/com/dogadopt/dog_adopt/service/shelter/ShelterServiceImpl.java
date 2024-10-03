package com.dogadopt.dog_adopt.service.shelter;

import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.domain.enums.address.Country;
import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import com.dogadopt.dog_adopt.dto.incoming.CreateUpdateAddressCommand;
import com.dogadopt.dog_adopt.dto.incoming.ShelterCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AddressInfo;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterInfo;
import com.dogadopt.dog_adopt.exception.ShelterAlreadyRegisteredException;
import com.dogadopt.dog_adopt.repository.ShelterRepository;
import com.dogadopt.dog_adopt.service.address.AddressService;
import com.dogadopt.dog_adopt.service.image.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ShelterServiceImpl implements ShelterService{

    private static final String SHELTER_FOLDER = "shelter";
    private static final ImageType SHELTER_IMAGE = ImageType.SHELTER;

    private final ShelterRepository shelterRepository;
    private final AddressService addressService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Override
    public ShelterInfo registerShelter(ShelterCreateUpdateCommand command) {

        Shelter shelter = modelMapper.map(command, Shelter.class);

        AddressInfo addressInfo = command.getAddressInfo();
        CreateUpdateAddressCommand addressCommand = modelMapper.map(addressInfo, CreateUpdateAddressCommand.class);
        Address address = addressService.registerAddress(addressCommand);

        shelter.setAddresses(new ArrayList<>(List.of(address)));
        address.setShelter(shelter);

        try {
            shelterRepository.save(shelter);
        } catch (DataIntegrityViolationException e) {
            throw new ShelterAlreadyRegisteredException("Shelter with same credentials have already been registered");
        }


        List<MultipartFile> multipartFiles = command.getImages();
        setImageToShelter(multipartFiles, shelter);

        ShelterInfo shelterInfo = modelMapper.map(shelter, ShelterInfo.class);
        shelterInfo.setAddressInfos(new ArrayList<>(List.of(addressInfo)));
        shelterInfo.setImageUrl(shelter.getImages().get(0).getUrl());
        return shelterInfo;
    }

    private void setImageToShelter(List<MultipartFile> multipartFiles, Shelter shelter) {

        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            List<MultipartFile> oneElementMultipartList = Collections.singletonList(multipartFiles.get(0));
            imageService.uploadFile(oneElementMultipartList, SHELTER_FOLDER, shelter.getId(), SHELTER_IMAGE);
        }
    }
}
