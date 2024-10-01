package com.dogadopt.dog_adopt.dto.incoming;

import com.dogadopt.dog_adopt.domain.Shelter;
import com.dogadopt.dog_adopt.domain.enums.dog.DogBreed;
import com.dogadopt.dog_adopt.domain.enums.dog.DogSize;
import com.dogadopt.dog_adopt.domain.enums.dog.DonationGoal;
import com.dogadopt.dog_adopt.domain.enums.dog.HealthStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DogCreateUpdateCommand {

    @NotNull
    @NotBlank(message = "Field must not be blank")
    @Size(max = 50, message = "Dog name can be maximum {max} characters.")
    private String name;

    @NotNull
    @PositiveOrZero(message = "Age must be positive or zero")
    private Integer age;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DogBreed breed;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DogSize dogSize;

    @NotNull
    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

    @Size(max = 500, message = "Description can have maximum {max} characters.")
    private String description;

    private List<MultipartFile> images;

    @NotNull
    private Shelter shelter;

    @Enumerated(EnumType.STRING)
    private DonationGoal donationGoal;

    private LocalDateTime takenToAdoptionCenter;
}
