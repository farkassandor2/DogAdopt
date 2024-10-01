package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.user.NewsLetterSubscriptionType;
import com.dogadopt.dog_adopt.domain.enums.user.UserLevel;
import com.dogadopt.dog_adopt.domain.enums.address.Country;
import com.dogadopt.dog_adopt.domain.enums.user.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private UserLevel userLevel;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private LocalDateTime createdAt;

    @NonNull
    @Enumerated(EnumType.STRING)
    private NewsLetterSubscriptionType newsLetterSubscriptionType;

    @OneToMany(mappedBy = "user")
    private List<DogAndUserAdoption> dogAndUserAdoptions;

    @OneToMany(mappedBy = "user")
    private List<DogAndUserFavorite> dogAndUserFavorite;

    @OneToMany(mappedBy = "user")
    private List<Donation> donations;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    private List<WalkingReservation> walkingReservations;

    @PrePersist
    protected void onCreate() {
        this.countryTelephoneCode = country
                .getTelephoneCountryCode();
        this.userLevel = UserLevel.PUPPY_IN_THE_PACK;
        this.userRole = UserRole.REGISTERED_USER;
        this.createdAt = LocalDateTime.now();
    }
}
