package com.dogadopt.dog_adopt.registration.token;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.exception.TokenNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ConfirmationToken generateToken(AppUser user) {

        String token = UUID.randomUUID().toString();
        String encodedToken = Base64.getEncoder().encodeToString(token.getBytes());
        ConfirmationToken confirmationToken = new ConfirmationToken(encodedToken,
                                                                    LocalDateTime.now(),
                                                                    LocalDateTime.now().plusMinutes(60),
                                                                    user);

        confirmationTokenRepository.save(confirmationToken);
        return confirmationToken;
    }

    @Override
    public ConfirmationToken getTokenByString(String token) {
        return confirmationTokenRepository
                .getTokenByString(token)
                .orElseThrow(() -> new TokenNotFoundException(token));
    }

    public void setConfirmedAtToNow(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.setConfirmedAtToNow(confirmationToken.getToken(), LocalDateTime.now());
    }

}
