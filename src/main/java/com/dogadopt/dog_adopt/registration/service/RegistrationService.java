package com.dogadopt.dog_adopt.registration.service;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import jakarta.validation.Valid;

import java.util.Map;

public interface RegistrationService {

    Map<String, String> registerUser(@Valid AppUserCreateCommand command);

    void confirmToken(String token);
}
