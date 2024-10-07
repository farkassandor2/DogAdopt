package com.dogadopt.dog_adopt.registration.service;

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
//    private final EmailSender emailSender;
    private final ConfirmationTokenService confirmationTokenService;
}
