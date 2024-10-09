package com.dogadopt.dog_adopt.registration.token;

import com.dogadopt.dog_adopt.domain.AppUser;

public interface ConfirmationTokenService {

    ConfirmationToken generateToken(AppUser user);

    ConfirmationToken getTokenByString(String token);

    void setConfirmedAtToNow(ConfirmationToken confirmationToken);

}
