package com.dogadopt.dog_adopt.dto.incoming;

import com.dogadopt.dog_adopt.domain.enums.address.Country;
import com.dogadopt.dog_adopt.domain.enums.user.NewsLetterSubscriptionType;
import com.dogadopt.dog_adopt.validation.password.Password;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserCreateUpdateCommand {

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

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private List<MultipartFile> images;

}
