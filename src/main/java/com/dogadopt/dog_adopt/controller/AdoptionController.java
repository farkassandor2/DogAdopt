package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.DogAndUserAdoptionCreateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogAndUserAdoptionInfo;
import com.dogadopt.dog_adopt.service.doganduseradoption.AdoptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @DeleteMapping("/{adoptionId}")
    @ResponseStatus(OK)
    public void deleteAdoptionByUser(@PathVariable Long adoptionId) {
        log.info("Http request / DELETE / api / adoption / adoptionId");
        adoptionService.deleteAdoptionByUser(adoptionId);
    }

    @DeleteMapping("/admin/{adoptionId}")
    @ResponseStatus(OK)
    public void deleteAdoptionByAdmin(@PathVariable Long adoptionId) {
        log.info("Http request / DELETE / api / adoption / admin / adoptionId");
        adoptionService.deleteAdoptionByAdmin(adoptionId);
    }

    @PatchMapping("/admin/update/{adoptionId}")
    @ResponseStatus(OK)
    public Map<String, String> updateAdoptionStatus(@PathVariable Long adoptionId,
                                                    @RequestBody Map<String, Object> updates) {
        log.info("Http request / PATCH / api / adoption / admin / adoptionId / update");
        return adoptionService.updateAdoption(adoptionId, updates);
    }
}
