package com.dogadopt.dog_adopt.service.shelter;

import com.dogadopt.dog_adopt.config.ObjectMapperUtil;
import com.dogadopt.dog_adopt.domain.Address;
import com.dogadopt.dog_adopt.domain.AddressShelter;
import com.dogadopt.dog_adopt.domain.Image;
import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import com.dogadopt.dog_adopt.dto.incoming.AddressCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.incoming.ImageUploadCommand;
import com.dogadopt.dog_adopt.dto.incoming.ShelterCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AddressInfo;
import com.dogadopt.dog_adopt.dto.outgoing.ImageInfo;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterDTOForDropDownMenu;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterInfoForUser;
import com.dogadopt.dog_adopt.exception.ShelterAlreadyRegisteredException;
import com.dogadopt.dog_adopt.exception.ShelterNotFoundException;
import com.dogadopt.dog_adopt.repository.ShelterRepository;
import com.dogadopt.dog_adopt.service.address.AddressService;
import com.dogadopt.dog_adopt.service.addressshelter.AddressShelterService;
import com.dogadopt.dog_adopt.service.image.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
    private final AddressShelterService addressShelterService;
    private final ModelMapper modelMapper;

    @Override
    public ShelterInfoForUser registerShelter(ShelterCreateUpdateCommand command) {

        Shelter shelter = modelMapper.map(command, Shelter.class);

        AddressInfo addressInfo = command.getAddressInfo();
        if (addressInfo != null) {
            AddressCreateUpdateCommand addressCommand = modelMapper.map(addressInfo, AddressCreateUpdateCommand.class);
            Address address = addressService.registerAddress(addressCommand);
            setAddressAndShelterToAddressShelter(address, shelter);
        }

        try {
            shelterRepository.save(shelter);
        } catch (DataIntegrityViolationException e) {
            throw new ShelterAlreadyRegisteredException("Shelter with same credentials have already been registered");
        }

        List<MultipartFile> multipartFiles = command.getImages();
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            setImageToShelter(multipartFiles, shelter);
        }

        ShelterInfoForUser shelterInfoForUser = modelMapper.map(shelter, ShelterInfoForUser.class);
        setAddressInfoAndShelterToShelterInfo(shelterInfoForUser, addressInfo, shelter);

        return shelterInfoForUser;
    }

    @Override
    public List<ShelterInfoForUser> listAllShelters() {
        List<Shelter> shelters = shelterRepository.findAll();
        List<ShelterInfoForUser> shelterInfoForUsers = ObjectMapperUtil.mapAll(shelters, ShelterInfoForUser.class);

        for (int i = 0; i < shelters.size(); i++) {

            Shelter actualShelter = shelters.get(i);

            Image imageOfActualShelter = imageService.getImagesForShelter(actualShelter);
            if (imageOfActualShelter != null) {
                shelterInfoForUsers.get(i).setImageInfo(
                        new ImageInfo(imageOfActualShelter.getId(),
                                      imageOfActualShelter.getImgUrl()));
            }

            List<Address> addressListOfActualShelter = addressService.getAddressesForShelter(actualShelter);
            List<AddressInfo> addressInfos = ObjectMapperUtil.mapAll(addressListOfActualShelter, AddressInfo.class);
            shelterInfoForUsers.get(i).setAddressInfos(addressInfos);
        }
        return shelterInfoForUsers;
    }

    @Override
    public List<ShelterDTOForDropDownMenu> getSheltersListForDropDown() {
        List<Shelter> shelters = shelterRepository.findAll();
        return ObjectMapperUtil.mapAll(shelters, ShelterDTOForDropDownMenu.class);
    }

    @Override
    public Shelter getShelter(Long shelterId) {
        return shelterRepository.findById(shelterId)
                .orElseThrow(() -> new ShelterNotFoundException("Shelter not found with ID: " + shelterId));
    }

    @Override
    public ShelterInfoForUser updateShelter(Long shelterId, Map<String, Object> updates) {
        Shelter shelter = getShelter(shelterId);

        updates.forEach((key, value) -> {
            switch(key) {
                case "name":
                    shelter.setName((String) value);
                    break;
                case "email":
                    shelter.setEmail((String) value);
                    break;
                case "phoneNumber":
                    shelter.setPhoneNumber((String) value);
                    break;
                case "description":
                    shelter.setDescription((String) value);
                    break;
                case "websiteUrl":
                    shelter.setWebsiteUrl((String) value);
                    break;
                default:
                    throw new ResponseStatusException(BAD_REQUEST, "Unknown field: " + key);
            }
        });

        shelterRepository.save(shelter);
        ShelterInfoForUser info = modelMapper.map(shelter, ShelterInfoForUser.class);

        List<Address> addressListOfActualShelter = addressService.getAddressesForShelter(shelter);
        List<AddressInfo> addressInfos = ObjectMapperUtil.mapAll(addressListOfActualShelter, AddressInfo.class);
        info.setAddressInfos(addressInfos);

        Image imageOfActualShelter = imageService.getImagesForShelter(shelter);

        if (imageOfActualShelter != null) {
            info.setImageInfo(new ImageInfo(imageOfActualShelter.getId(), imageOfActualShelter.getImgUrl()));
        }

        return info;
    }

    @Override
    public List<AddressInfo> addNewAddress(Long shelterId, AddressCreateUpdateCommand command) {
        Shelter shelter = getShelter(shelterId);
        Address newAddress = addressService.registerAddress(command);

        setAddressAndShelterToAddressShelter(newAddress, shelter);

        List<Address> addresses = addressService.getAddressesForShelter(shelter);

        return ObjectMapperUtil.mapAll(addresses, AddressInfo.class);
    }

    @Override
    public void deleteConnectionBetweenShelterAndAddress(Long shelterId, Long addressId) {
        Shelter shelter = getShelter(shelterId);
        Address address = addressService.getAddressById(addressId);
        addressShelterService.deleteConnectionBetweenShelterAndAddress(shelter, address);
    }

    @Override
    public void changePicture(Long shelterId, ImageUploadCommand command) {

        Shelter shelter = getShelter(shelterId);
        Long imgId = null;

        try {
            imgId = shelter.getImages().get(0).getId();
        } catch (IndexOutOfBoundsException e) {
            log.info("No image to change.");
        }

        if (imgId != null) {
            imageService.deleteImage(imgId, SHELTER_FOLDER);
        }

        List<MultipartFile> multipartFiles = command.getImages();
        setImageToShelter(multipartFiles, shelter);
    }

    @Override
    public void deletePictureForShelter(Long pictureId) {
        imageService.deleteImage(pictureId, SHELTER_FOLDER);
    }

    private void setImageToShelter(List<MultipartFile> multipartFiles, Shelter shelter) {
        List<Image> images = new ArrayList<>();

        images = saveImages(multipartFiles, shelter, images);
        shelter.setImages(images);
        if (images != null && !images.isEmpty()) {
            images.get(0).setShelter(shelter);
        }
    }

    private List<Image> saveImages(List<MultipartFile> multipartFiles, Shelter shelter, List<Image> images) {
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            List<MultipartFile> oneElementMultipartList = Collections.singletonList(multipartFiles.get(0));
            images = imageService.uploadFile(oneElementMultipartList, SHELTER_FOLDER, shelter.getId(), SHELTER_IMAGE);
        }
        return images;
    }

    private void setAddressInfoAndShelterToShelterInfo(ShelterInfoForUser shelterInfoForUser, AddressInfo addressInfo, Shelter shelter) {

        if (addressInfo != null) {
            shelterInfoForUser.setAddressInfos(new ArrayList<>(List.of(addressInfo)));
        }

        if (shelter.getImages() != null) {
            shelterInfoForUser.setImageInfo(new ImageInfo(shelter.getId(), shelter.getImages().get(0).getImgUrl()));
        }
    }

    private void setAddressAndShelterToAddressShelter(Address address, Shelter shelter) {
        AddressShelter addressShelter = new AddressShelter();
        addressShelter.setAddress(address);
        addressShelter.setShelter(shelter);
        addressShelterService.save(addressShelter);

        if (shelter.getAddressShelters() != null) {
            shelter.getAddressShelters().add(addressShelter);
        } else {
            shelter.setAddressShelters(new ArrayList<>(List.of(addressShelter)));
        }

        if (address.getAddressShelters() != null) {
            address.getAddressShelters().add(addressShelter);
        } else {
            address.setAddressShelters(new ArrayList<>(List.of(addressShelter)));
        }
    }
}
