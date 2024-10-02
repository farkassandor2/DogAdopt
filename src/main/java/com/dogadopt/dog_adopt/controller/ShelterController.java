package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.ShelterCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.ShelterInfo;
import com.dogadopt.dog_adopt.service.shelter.ShelterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/shelter")
@RequiredArgsConstructor
@Slf4j
public class ShelterController {

    private final ShelterService shelterService;

    @PostMapping("/admin/register")
    @ResponseStatus(CREATED)
    public ShelterInfo registerShelter(@Valid @ModelAttribute ShelterCreateUpdateCommand command) {
        log.info("Http request / POST / dog-adopt / dog / register, body: {}", command.toString());
        log.info("HTTP request / POST / dop-adopt / shelter / admin / register, body {}", command.toString());
        return shelterService.registerShelter(command);
    }


}
