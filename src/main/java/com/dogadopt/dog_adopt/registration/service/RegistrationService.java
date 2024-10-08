package com.dogadopt.dog_adopt.registration.service;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateUpdateCommand;
import jakarta.validation.Valid;

public interface RegistrationService {

    String registerUser(@Valid AppUserCreateUpdateCommand command);
}
