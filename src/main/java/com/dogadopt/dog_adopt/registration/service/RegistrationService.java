package com.dogadopt.dog_adopt.registration.service;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import jakarta.validation.Valid;

public interface RegistrationService {

    String registerUser(@Valid AppUserCreateCommand command);
}
