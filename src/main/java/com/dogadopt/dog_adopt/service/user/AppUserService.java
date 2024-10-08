package com.dogadopt.dog_adopt.service.user;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateCommand;
import com.dogadopt.dog_adopt.dto.incoming.AppUserUpdateCommand;
import jakarta.validation.Valid;

public interface AppUserService {

    String updateUser(@Valid AppUserUpdateCommand command);

    String registerCustomer(AppUserCreateCommand command);
}
