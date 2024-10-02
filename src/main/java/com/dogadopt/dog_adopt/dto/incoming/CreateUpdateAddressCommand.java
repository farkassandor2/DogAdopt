package com.dogadopt.dog_adopt.dto.incoming;

import com.dogadopt.dog_adopt.domain.enums.address.Country;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateAddressCommand {

    @NotNull
    private String zip;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Country country;

    @NotNull
    @Size(max = 50, message = "City name can be maximum {max} character.")
    private String city;

    @NotNull
    @Size(max = 50, message = "City name can be maximum {max} character.")
    private String street;

    private String houseNumber;
}
