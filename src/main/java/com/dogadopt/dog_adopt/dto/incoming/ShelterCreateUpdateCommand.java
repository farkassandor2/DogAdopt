package com.dogadopt.dog_adopt.dto.incoming;

import com.dogadopt.dog_adopt.domain.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShelterCreateUpdateCommand {

    @NotNull
    @Size(max = 75, message = "Description can hava maximum {max} characters.")
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phoneNumber;

    @Size(max = 1000, message = "Description can hava maximum {max} characters.")
    private String description;

    @Pattern(regexp = "^(https?://)?(www\\.)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}(/\\S*)?$",
             message = "Invalid URL format")
    private String websiteUrl;

    private List<AddressInfo> addressInfos;

    private List<MultipartFile> images;
}
