package com.dogadopt.dog_adopt.registration.service;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.dto.incoming.PasswordResetCommand;
import com.dogadopt.dog_adopt.dto.incoming.PasswordResetRequest;
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

    private static final String MESSAGE = "message";

    private final AppUserService appUserService;
    private final EmailSenderService emailSenderService;
    private final EmailTemplateService emailTemplateService;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public Map<String, String> registerUser(AppUserCreateCommand command) {

        Map<String, String> reply = new HashMap<>();
        String email = command.getEmail();

        boolean isExistingEmailInactiveUSer = checkIfEmailAlreadyRegisteredAndInactive(email);

        if (isExistingEmailInactiveUSer) {
            reply.put(MESSAGE, "E-mail already registered. If you wish to activate select 'Resend confirmation e-mail'");
        } else {
            String token = appUserService.registerCustomer(command);
            sendConfirmationEmail(token, email, reply);
        }
        return reply;
    }

    @Override
    public Map<String, String> resendConfirmationEmail(Map<String, String> email) {
        Map<String, String> reply = new HashMap<>();
        String emailString = email.get("email");
        AppUser user = appUserService.findUserByEmail(emailString);
        ConfirmationToken token = confirmationTokenService.generateToken(user);
        sendConfirmationEmail(token.getToken(), emailString, reply);
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
        response.put(MESSAGE, "Token confirmed successfully. You can now log in.");
        return response;
    }

    @Override
    public Map<String, String> requestResetPassword(PasswordResetRequest command) {

        String emailAddress = command.getEmail();
        Map<String, String> reply = new HashMap<>();

        String letterTitle = "Change of Password";
        String text1 = "Please click on the below link to change your password:";
        String text2 = "Change Password Now";
        String text3 = "";

        if (emailAddress != null && !emailAddress.isEmpty()) {
            AppUser user = appUserService.findUserByEmail(emailAddress);
            ConfirmationToken token = confirmationTokenService.generateToken(user);
            String link = "http://localhost:4200/password/reset?token=" + token.getToken();

            emailSenderService.send(
                    emailAddress,
                    emailTemplateService.buildEmail(letterTitle, emailAddress, link, text1, text2, text3),
                    "Change your password");

            reply.put(MESSAGE, "Please check your email to reset your password");
        } else {
            reply.put(MESSAGE, "Please enter a valid e-mail address");
        }
        return reply;
    }

    @Override
    public Map<String, String> resetPassword(PasswordResetCommand command) {

        Map<String, String> reply = new HashMap<>();
        String password = command.getPassword();

        if (password != null && !password.isEmpty()) {
            String encodedPassword = appUserService.encodePassword(password);
            String token = command.getToken();
            AppUser user = appUserService.findUserByToken(token);
            appUserService.setPasswordToUser(user, encodedPassword);
            reply = confirmToken(token);
        } else {
            reply.put(MESSAGE, "Problem saving new password. Please try again!");
        }

        return reply;
    }

    private boolean checkIfEmailAlreadyRegisteredAndInactive(String email) {
        return appUserService.checkIfEmailAlreadyRegisteredAndInactive(email);
    }

    private void sendConfirmationEmail(String token, String email, Map<String, String> reply) {
        String letterTitle = "Confirm your email";
        String text1 = "Thank you for registering. Please click on the below link to activate your account:";
        String text2 = "Activate Now";
        String text3 = "Link will expire in 60 minutes.";

        String link = "http://localhost:8080/api/registration/confirm?token=" + token;

        String emailContent = emailTemplateService
                .buildEmail(letterTitle, email, text1, text2, text3, link);

        try {
            emailSenderService.send(
                    email,
                    emailContent,
                    "Confirm your email");
        } catch (Exception e) {
            reply.put(MESSAGE, "Failed to send email to" + email);
        }
        reply.put(MESSAGE, "Registration successful. Please check your email to confirm.");
    }
}
