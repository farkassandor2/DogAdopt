package com.dogadopt.dog_adopt.service.user;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.dto.incoming.AppUserUpdateCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public interface AppUserService {

    String updateUser(@Valid AppUserUpdateCommand command);

    String registerCustomer(AppUserCreateCommand command);

    void setPasswordToUser(AppUser user, String encodedPassword);

    String encodePassword(@NotNull String password);

    void enableCustomer(@NonNull @Email String email);

    AppUser getUserByEmail(String emailAddress);

    AppUser getUserByToken(String token);
}
