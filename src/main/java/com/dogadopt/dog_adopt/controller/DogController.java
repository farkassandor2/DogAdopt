package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoListOfDogs;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import com.dogadopt.dog_adopt.service.dog.DogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/dogs")
@Slf4j
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;

    @PostMapping("/admin/register")
    @ResponseStatus(CREATED)
    public DogInfoOneDog registerDog(@Valid @ModelAttribute DogCreateUpdateCommand command) {
        log.info("Http request / POST / dog-adopt / dog / admin / register, body: {}", command.toString());
        return dogService.registerDog(command);
    }

    @GetMapping("/all-dogs")
    @ResponseStatus(OK)
    public List<DogInfoListOfDogs> listAllDogs() {
        log.info("Http request / GET / dog-adopt / all-dogs");
        return dogService.listAllDogs();
    }

    @GetMapping("/{dogId}")
    @ResponseStatus(OK)
    public DogInfoOneDog getOneDog(@PathVariable Long dogId) {
        log.info("Http request / GET / dog-adopt / dogId");
        return dogService.getOneDogInfo(dogId);
    }

    @GetMapping("/get-dogs-shelter/{shelterId}")
    @ResponseStatus(OK)
    public List<DogInfoListOfDogs> getAllDogsFromShelter(@PathVariable Long shelterId) {
        log.info("Http request / GET / dog-adopt / shelters / shelterId / dogs");
        return dogService.getAllDogsFromShelter(shelterId);
    }

    @PatchMapping("/admin/update/{dogId}")
    @ResponseStatus(OK)
    public DogInfoOneDog updateDog(@PathVariable Long dogId, @Valid @RequestBody Map<String, Object> updates) {
        log.info("Http request / GET / dog-adopt / admin / update / dogId");
        return dogService.updateDog(dogId, updates);
    }



}
