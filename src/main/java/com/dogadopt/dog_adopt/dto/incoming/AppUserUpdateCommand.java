package com.dogadopt.dog_adopt.dto.incoming;

import com.dogadopt.dog_adopt.domain.enums.address.Country;
import com.dogadopt.dog_adopt.validation.password.Password;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserUpdateCommand {

    private String email;

    @Password
    private String password;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Size(max = 50, message = "Firstname can have maximum {max} characters.")
    private String firstName;

    @Size(max = 50, message = "Firstname can have maximum {max} characters.")
    private String lastName;

    private String phoneNumber;

}
