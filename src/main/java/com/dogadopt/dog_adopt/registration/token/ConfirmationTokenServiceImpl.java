package com.dogadopt.dog_adopt.registration.token;

import com.dogadopt.dog_adopt.domain.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ConfirmationToken generateToken(AppUser user) {

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                                                                    LocalDateTime.now(),
                                                                    LocalDateTime.now().plusMinutes(60),
                                                                    user);

        confirmationTokenRepository.save(confirmationToken);
        return confirmationToken;
    }
}
