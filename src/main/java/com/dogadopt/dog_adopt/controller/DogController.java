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
        log.info("Http request / POST / dog-adopt / dog / register, body: {}", command.toString());
        return dogService.registerDog(command);
    }

    @GetMapping("/all-dogs")
    public List<DogInfoListOfDogs> listAllDogs() {
        log.info("Http request / POST / dog-adopt / dog / register");
        return dogService.listAllDogs();
    }
}
