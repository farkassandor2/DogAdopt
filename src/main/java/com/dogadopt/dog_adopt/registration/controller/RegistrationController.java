package com.dogadopt.dog_adopt.registration.controller;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.dto.incoming.PasswordResetCommand;
import com.dogadopt.dog_adopt.dto.incoming.PasswordResetRequest;
import com.dogadopt.dog_adopt.registration.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/registration")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Map<String, String> registerUser(@Valid @RequestBody AppUserCreateCommand command) {
        log.info("Http request, POST / api / registration , body: {}", command.toString());
        return registrationService.registerUser(command);
    }

    @GetMapping("/confirm")
    @ResponseStatus(OK)
    public Map<String, String> confirm(@RequestParam("token") String token) {
        log.info("Http request, GET / api / registration / confirm , body: {}", token);
        return registrationService.confirmToken(token);
    }

    @PostMapping("/password/request")
    @ResponseStatus(OK)
    public Map<String, String> requestResetPassword(@Valid @RequestBody PasswordResetRequest command) {
        log.info("Http request, POST / api / registration / password / request, body: {}", command.toString());
        return registrationService.requestResetPassword(command);
    }

    @PostMapping("/password/reset")
    @ResponseStatus(OK)
    public Map<String, String> resetPassword(@Valid @RequestBody PasswordResetCommand command) {
        log.info("Http request, POST / api / registration / password / reset, body: {}", command.toString());
        return registrationService.resetPassword(command);
    }
}
