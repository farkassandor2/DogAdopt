package com.dogadopt.dog_adopt.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(max = 75, message = "Description can hava maximum {max} characters.")
    private String name;

    @NonNull
    @Email
    private String email;

    private String countryTelephoneCode;

    @NonNull
    private String phoneNumber;

    @Size(max = 1000, message = "Description can hava maximum {max} characters.")
    private String description;

    @Pattern(regexp = "^(https?://)?(www\\.)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}(/\\S*)?$",
             message = "Invalid URL format")
    private String websiteUrl;

    private LocalDateTime registeredOnWebsite;

    @OneToMany(mappedBy = "shelter")
    private List<Address> addresses;

    @OneToMany(mappedBy = "shelter")
    private List<Dog> dogs;

    @OneToMany(mappedBy = "shelter")
    private List<Donation> donations;

    @OneToMany(mappedBy = "shelter")
    private List<Image> images;

    @PrePersist
    protected void onCreate() {
        this.registeredOnWebsite = LocalDateTime.now();
        this.countryTelephoneCode = addresses
                .get(0)
                .getCountry()
                .getTelephoneCountryCode();
    }
}
