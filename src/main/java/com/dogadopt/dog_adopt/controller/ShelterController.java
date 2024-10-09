package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.AddressCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.incoming.ImageUploadCommand;
import com.dogadopt.dog_adopt.dto.incoming.ShelterCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AddressInfo;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterDTOForDropDownMenu;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterInfoForUser;
import com.dogadopt.dog_adopt.service.shelter.ShelterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/shelters")
@RequiredArgsConstructor
@Slf4j
public class ShelterController {

    private final ShelterService shelterService;

    @PostMapping("/admin/register")
    @ResponseStatus(CREATED)
    public ShelterInfoForUser registerShelter(@Valid @ModelAttribute ShelterCreateUpdateCommand command) {
        log.info("HTTP request / POST / dop-adopt / shelters / admin / register, body {}", command.toString());
        return shelterService.registerShelter(command);
    }

    @GetMapping("/all-shelters")
    @ResponseStatus(OK)
    public List<ShelterInfoForUser> listAllSheltersForUserRequest() {
        log.info("HTTP request / GET / dop-adopt / shelters / all-shelters");
        return shelterService.listAllShelters();
    }

    @GetMapping("/admin/for-dropdown")
    @ResponseStatus(OK)
    public List<ShelterDTOForDropDownMenu> getSheltersListForDropdown() {
        log.info("Http request / GET / dog-adopt / shelters / for-dropdown");
        return shelterService.getSheltersListForDropDown();
    }

    @PatchMapping("/admin/update/{shelterId}")
    @ResponseStatus(OK)
    public ShelterInfoForUser updateShelter(@PathVariable Long shelterId, @Valid @RequestBody Map<String, Object> updates) {
        log.info("Http request / PATCH / dog-adopt / shelters / admin / update / shelterId");
        return shelterService.updateShelter(shelterId, updates);
    }

    @PutMapping("/admin/update-address/{shelterId}")
    @ResponseStatus(OK)
    public List<AddressInfo> addNewAddress(@PathVariable Long shelterId, @Valid @RequestBody AddressCreateUpdateCommand command) {
        log.info("HTTP request / PUT / dop-adopt  / shelters / admin / addresses / update, body {}", command.toString());
        return shelterService.addNewAddress(shelterId, command);
    }

    @DeleteMapping("/admin/delete-address/{shelterId}/{addressId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteAddress(@PathVariable Long shelterId, @PathVariable Long addressId) {
        log.info("Http request / DELETE / dog-adopt / shelters / admin / delete-address / shelterId / addressId, shelterId {} addressId{}", shelterId, addressId);
        shelterService.deleteConnectionBetweenShelterAndAddress(shelterId, addressId);
    }

    @PutMapping("/admin/change-picture/{shelterId}")
    @ResponseStatus(OK)
    public void changePicture(@PathVariable Long shelterId, @ModelAttribute ImageUploadCommand command) {
        log.info("Http request / PUT / dog-adopt / shelters / admin / change-picture / shelterId");
        shelterService.changePicture(shelterId, command);
    }

    @DeleteMapping("/admin/delete-picture/{pictureId}")
    @ResponseStatus(NO_CONTENT)
    public void deletePictureForShelter(@PathVariable Long pictureId) {
        log.info("Http request / DELETE / dog-adopt / shelters / admin / delete-picture / pictureId");
        shelterService.deletePictureForShelter(pictureId);
    }

}
