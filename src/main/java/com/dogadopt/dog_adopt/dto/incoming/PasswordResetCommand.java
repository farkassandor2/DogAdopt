package com.dogadopt.dog_adopt.dto.incoming;

import com.dogadopt.dog_adopt.validation.password.Password;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetCommand {

    @NotNull
    @Password
    private String password;

    private String token;
}
