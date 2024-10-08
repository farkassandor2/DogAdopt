package com.dogadopt.dog_adopt.registration.service;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.email.EmailSenderService;
import com.dogadopt.dog_adopt.registration.token.ConfirmationTokenService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService{

    private final AppUserService appUserService;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public String registerUser(AppUserCreateCommand command) {

        String token = appUserService.registerCustomer(command);

        String text1 = "Thank you for registering. Please click on the below link to activate your account:";
        String text2 = "Activate Now";
        String text3 = "Link will expire in 60 minutes.";

        String link = "http://localhost:8080/dog-adopt/registration/confirm?token=" + token;

        emailSenderService.send(
                command.getEmail(),
"THIS LINE NEEDS TO BE CHANGED",
//                buildEmail(link, text1, text2, text3),
                "Confirm your email");

        return token;
    }
}
