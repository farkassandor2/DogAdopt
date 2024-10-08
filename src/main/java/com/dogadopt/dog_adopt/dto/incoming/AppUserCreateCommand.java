package com.dogadopt.dog_adopt.dto.incoming;

import com.dogadopt.dog_adopt.domain.enums.address.Country;
import com.dogadopt.dog_adopt.validation.password.Password;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserCreateCommand {

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @Password
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Country country;

}
