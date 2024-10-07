package com.dogadopt.dog_adopt.registration.controller;

import com.dogadopt.dog_adopt.registration.service.RegistrationService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserService appUserService;
}
