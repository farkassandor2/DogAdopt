package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.ShelterCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterDTOForDropDownMenu;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterInfoForUser;
import com.dogadopt.dog_adopt.service.shelter.ShelterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/shelters")
@RequiredArgsConstructor
@Slf4j
public class ShelterController {

    private final ShelterService shelterService;

    @PostMapping("/admin/register")
    @ResponseStatus(CREATED)
    public ShelterInfoForUser registerShelter(@Valid @ModelAttribute ShelterCreateUpdateCommand command) {
        log.info("HTTP request / POST / dop-adopt / shelter / admin / register, body {}", command.toString());
        return shelterService.registerShelter(command);
    }

    @GetMapping("/all-shelters")
    @ResponseStatus(OK)
    public List<ShelterInfoForUser> listAllSheltersForUserRequest() {
        log.info("HTTP request / GET / dop-adopt / shelter / all-shelters");
        return shelterService.listAllShelters();
    }

    @RequestMapping("/admin/for-dropdown")
    @ResponseStatus(OK)
    public List<ShelterDTOForDropDownMenu> getSheltersListForDropdown() {
        log.info("Http request / GET / dog-adopt / shelters / for-dropdown");
        return shelterService.getSheltersListForDropDown();
    }


}
