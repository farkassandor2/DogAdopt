package com.dogadopt.dog_adopt.registration.service;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.email.build.EmailTemplateService;
import com.dogadopt.dog_adopt.email.send.EmailSenderService;
import com.dogadopt.dog_adopt.exception.TokenHasAlreadyBeenConfirmed;
import com.dogadopt.dog_adopt.exception.TokesHasAlreadyExpired;
import com.dogadopt.dog_adopt.registration.token.ConfirmationToken;
import com.dogadopt.dog_adopt.registration.token.ConfirmationTokenService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService{

    private final AppUserService appUserService;
    private final EmailSenderService emailSenderService;
    private final EmailTemplateService emailTemplateService;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public Map<String, String> registerUser(AppUserCreateCommand command) {

        Map<String, String> reply = new HashMap<>();
        String token = appUserService.registerCustomer(command);

        String text1 = "Thank you for registering. Please click on the below link to activate your account:";
        String text2 = "Activate Now";
        String text3 = "Link will expire in 60 minutes.";

        String link = "http://localhost:8080/dog-adopt/registration/confirm?token=" + token;

        String emailContent = emailTemplateService
                .buildConfirmationEmail(command.getEmail(), link, text1, text2, text3);

        try {
            emailSenderService.send(
                    command.getEmail(),
                    emailContent,
                    "Confirm your email");
        } catch (Exception e) {
            reply.put("message", "Failed to send email to" + command.getEmail());
        }

        reply.put("message", "Registration successful. Please check your email to confirm.");

        return reply;
    }

    @Override
    public Map<String, String> confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getTokenByString(token);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new TokenHasAlreadyBeenConfirmed(token);
        }

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokesHasAlreadyExpired(token);
        }

        confirmationTokenService.setConfirmedAtToNow(confirmationToken);

        appUserService.enableCustomer(confirmationToken
                                               .getUser()
                                               .getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Token confirmed successfully. You can now log in.");
        return response;
    }

    @Override
    public Map<String, String> requestResetPassword(String emailAddress) {

        Map<String, String> reply = new HashMap<>();

        String text1 = "Please click on the below link to change your password:";
        String text2 = "Change Password Now";
        String text3 = "";

        if (emailAddress != null && !emailAddress.isEmpty()) {
            appUserService.getUserByEmail(emailAddress);
            String link = "http://localhost:8080/dog-adopt/password/reset?email=" + emailAddress;

            emailSenderService.send(
                    emailAddress,
                    emailTemplateService.buildConfirmationEmail(emailAddress, link, text1, text2, text3),
                    "Change your password");

            reply.put("message", "Please check your email to reset your password");
        } else {
            reply.put("message", "Please enter a valid e-mail address");
        }
        return reply;
    }

    @Override
    public Map<String, String> resetPassword(String email) {
        return Map.of();
    }
}
