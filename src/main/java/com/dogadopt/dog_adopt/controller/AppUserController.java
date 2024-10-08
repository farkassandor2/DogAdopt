package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateUpdateCommand;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping("/update")
    @ResponseStatus(OK)
    public String updateUser(@Valid @RequestBody AppUserCreateUpdateCommand command) {
        log.info("Http request, POST / dog-adopt / users / update , body: {}", command.toString());
        return appUserService.updateUser(command);
    }
}
