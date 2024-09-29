package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.UserLevel;
import com.dogadopt.dog_adopt.domain.enums.address.Country;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")})
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String email;

    private String countryTelephoneCode;

    @NonNull
    private String phoneNumber;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Country country;

    @OneToMany(mappedBy = "user")
    private List<Image> images;

    private int totalAmountOfDonation;

    private UserLevel userLevel;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.countryTelephoneCode = country
                .getTelephoneCountryCode();
        this.totalAmountOfDonation = 0;
        this.userLevel = UserLevel.PUPPY_IN_THE_PACK;
        this.createdAt = LocalDateTime.now();
    }
}
