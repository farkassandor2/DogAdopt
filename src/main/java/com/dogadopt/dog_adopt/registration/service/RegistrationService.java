package com.dogadopt.dog_adopt.registration.service;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.dto.incoming.PasswordResetCommand;
import com.dogadopt.dog_adopt.dto.incoming.PasswordResetRequest;
import jakarta.validation.Valid;

import java.util.Map;

public interface RegistrationService {

    Map<String, String> registerUser(@Valid AppUserCreateCommand command);

    Map<String, String> confirmToken(String token);

    Map<String, String> requestResetPassword(PasswordResetRequest command);

    Map<String, String> resetPassword(PasswordResetCommand command);

    Map<String, String> resendConfirmationEmail(Map<String, String> email);
}
