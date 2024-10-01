package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.dog.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.dog.DogInfo;
import com.dogadopt.dog_adopt.service.dog.DogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dog")
@Slf4j
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;

    public DogInfo registerDog(@Valid DogCreateUpdateCommand command) {
        log.info("Http request / POST / dog-adopt / dog / register, body: {}", command.toString());
        return dogService.registerDog(command);
    }
}
