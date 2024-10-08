package com.dogadopt.dog_adopt.service.user;

import com.dogadopt.dog_adopt.dto.incoming.AppUserCreateUpdateCommand;
import jakarta.validation.Valid;

public interface AppUserService {

    String updateUser(@Valid AppUserCreateUpdateCommand command);
}
