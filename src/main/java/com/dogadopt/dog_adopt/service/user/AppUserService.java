package com.dogadopt.dog_adopt.service.user;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.dto.incoming.AppUserUpdateCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.NonNull;

public interface AppUserService {

    String updateUser(@Valid AppUserUpdateCommand command);

    String registerCustomer(AppUserCreateCommand command);

    void enableCustomer(@NonNull @Email String email);
}
