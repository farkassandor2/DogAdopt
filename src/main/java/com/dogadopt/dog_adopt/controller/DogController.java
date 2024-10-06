package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.incoming.ImageUploadCommand;
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
        log.info("Http request / POST / dog-adopt / dogs / admin / register, body: {}", command.toString());
        return dogService.registerDog(command);
    }

    @GetMapping("/all-dogs")
    @ResponseStatus(OK)
    public List<DogInfoListOfDogs> listAllDogs() {
        log.info("Http request / GET / dog-adopt /dogs / all-dogs");
        return dogService.listAllDogs();
    }

    @GetMapping("/{dogId}")
    @ResponseStatus(OK)
    public DogInfoOneDog getOneDog(@PathVariable Long dogId) {
        log.info("Http request / GET / dog-adopt / dogs / dogId");
        return dogService.getOneDogInfo(dogId);
    }

    @GetMapping("/get-dogs-shelter/{shelterId}")
    @ResponseStatus(OK)
    public List<DogInfoListOfDogs> getAllDogsFromShelter(@PathVariable Long shelterId) {
        log.info("Http request / GET / dog-adopt / dogs / get-dogs-shelter / shelterId / dogs");
        return dogService.getAllDogsFromShelter(shelterId);
    }

    @PatchMapping("/admin/update/{dogId}")
    @ResponseStatus(OK)
    public DogInfoOneDog updateDog(@PathVariable Long dogId, @Valid @RequestBody Map<String, Object> updates) {
        log.info("Http request / PATCH / dog-adopt / dogs / admin / update / dogId");
        return dogService.updateDog(dogId, updates);
    }

//    @DeleteMapping("/admin/delete-picture/{dogId}/{pictureId}")
//    @ResponseStatus(NO_CONTENT)
//    public void deletePictureForDog(@PathVariable Long dogId, @PathVariable Long pictureId) {
//        log.info("Http request / DELETE / dog-adopt / dogs / admin / delete-picture / dogId / pictureId");
//        return dogService.deletePictureForDog(dogId, pictureId);
//    }

    @PutMapping("/admin/upload-picture/{dogId}")
    @ResponseStatus(OK)
    public void uploadPictureForDog(@PathVariable Long dogId, @ModelAttribute ImageUploadCommand command) {
        log.info("Http request / PUT / dog-adopt / dogs / admin / upload-picture / dogId, body {}", command.toString());
        dogService.uploadPictureForDog(dogId, command);
    }

}
