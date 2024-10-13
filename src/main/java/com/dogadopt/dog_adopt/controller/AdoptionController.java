package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.DogAndUserAdoptionCreateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogAndUserAdoptionInfo;
import com.dogadopt.dog_adopt.service.doganduseradoption.AdoptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/adoption")
@RequiredArgsConstructor
@Slf4j
public class AdoptionController {

    private final AdoptionService adoptionService;

    @PostMapping("/{userId}/{dogId}")
    @ResponseStatus(CREATED)
    public DogAndUserAdoptionInfo adopt(@PathVariable Long userId,
                                        @PathVariable Long dogId,
                                        @Valid @RequestBody DogAndUserAdoptionCreateCommand command) {
        log.info("Http request / POST / api / adoption / userId / dogId, body: {}", command.toString());
        return adoptionService.adopt(userId, dogId, command);
    }

    @PatchMapping("/{adoptionId}")
    @ResponseStatus(OK)
    public void deleteAdoption(@PathVariable Long adoptionId) {
        log.info("Http request / DELETE / api / adoption / adoptionId");
        adoptionService.deleteAdoption(adoptionId);
    }


    //admin -- to change dogs status
}
