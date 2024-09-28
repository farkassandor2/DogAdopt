package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.shelter.Country;
import com.dogadopt.dog_adopt.validation.zip.Zip;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Zip
    private String zip;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Country country;

    @NonNull
    @Size(max = 50, message = "City name can be maximum {max} character.")
    private String city;

    @NonNull
    @Size(max = 50, message = "City name can be maximum {max} character.")
    private String street;

    private String houseNumber;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
}
