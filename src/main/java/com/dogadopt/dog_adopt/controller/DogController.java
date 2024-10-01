package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import com.dogadopt.dog_adopt.service.dog.DogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/dogs")
@Slf4j
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public DogInfoOneDog registerDog(@Valid DogCreateUpdateCommand command) {
        log.info("Http request / POST / dog-adopt / dog / register, body: {}", command.toString());
        return dogService.registerDog(command);
    }
}
