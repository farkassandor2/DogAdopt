package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.DogAndUserAdoptionCreateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogAndUserAdoptionInfo;
import com.dogadopt.dog_adopt.service.doganduseradoption.DogAndUserAdoptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/adoption")
@RequiredArgsConstructor
@Slf4j
public class DogAndUserAdoptionController {

    private final DogAndUserAdoptionService dogAndUserAdoptionService;

    @PostMapping("/{userId}/{dogId}")
    @ResponseStatus(CREATED)
    public DogAndUserAdoptionInfo adopt(@PathVariable Long userId,
                                        @PathVariable Long dogId,
                                        @Valid @RequestBody DogAndUserAdoptionCreateCommand command) {
        log.info("Http request / POST / api / adoption / userId / dogId, body: {}", command.toString());
        return dogAndUserAdoptionService.adopt(userId, dogId, command);
    }

    //delete adoption
    //change adoption type
    //admin -- to change dogs status
}
