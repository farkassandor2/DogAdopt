package com.dogadopt.dog_adopt.registration.controller;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.registration.service.RegistrationService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
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
    private final AppUserService appUserService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Map<String, String> registerUser(@Valid @RequestBody AppUserCreateCommand command) {
        log.info("Http request, POST / dog-adopt / registration , body: {}", command.toString());
        return registrationService.registerUser(command);
    }

    @GetMapping("/confirm")
    @ResponseStatus(OK)
    public Map<String, String> confirm(@RequestParam("token") String token) {
        log.info("Http request, GET / dog-adopt / registration / confirm , body: {}", token);
        return registrationService.confirmToken(token);
    }

    @PostMapping("/password/new")
    @ResponseStatus(OK)
    public Map<String, String> requestResetPassword(@Valid @RequestBody Map<String, String> emailMap) {
        log.info("Http request, POST / dog-adopt / registration / password / new, email: {}", emailMap);
        return registrationService.requestResetPassword(emailMap.get("email"));
    }

    @PostMapping("/password/reset")
    @ResponseStatus(OK)
    public Map<String, String> resetPassword(@Valid @RequestBody Map<String, String> newPasswordMap) {
        log.info("Http request, POST / dog-adopt / registration / password / reset, newPassword: {}", newPasswordMap);
        return registrationService.resetPassword(newPasswordMap.get("password"));
    }
}
