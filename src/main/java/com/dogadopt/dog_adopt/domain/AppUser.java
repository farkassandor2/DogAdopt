package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.user.NewsLetterSubscriptionType;
import com.dogadopt.dog_adopt.domain.enums.user.UserLevel;
import com.dogadopt.dog_adopt.domain.enums.address.Country;
import com.dogadopt.dog_adopt.domain.enums.user.UserRole;
import com.dogadopt.dog_adopt.registration.token.ConfirmationToken;
import com.dogadopt.dog_adopt.validation.password.Password;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")})
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @NonNull
    @Email
    private String email;

    private String countryTelephoneCode;

    private String phoneNumber;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Country country;

    @OneToMany(mappedBy = "user")
    private List<Image> images;

    @NonNull
    @Password
    private String password;

    private boolean enabled = false;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = false;

    private boolean credentialsNonExpired = true;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "role")
    private List<UserRole> roles = new ArrayList<>(List.of(UserRole.REGISTERED_USER));

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays
                .stream(this
                                .getRoles()
                                .stream()
                                .map(Enum::name)
                                .toArray(String[]::new))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Enumerated(EnumType.STRING)
    private UserLevel userLevel;

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

    @OneToMany(mappedBy = "user")
    private List<ConfirmationToken> confirmationTokens;

    @PrePersist
    protected void onCreate() {
        this.countryTelephoneCode = country
                .getTelephoneCountryCode();
        this.userLevel = UserLevel.PUPPY_IN_THE_PACK;
        this.createdAt = LocalDateTime.now();
        this.newsLetterSubscriptionType = NewsLetterSubscriptionType.SUBSCRIBED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser user = (AppUser) o;
        return Objects.equals(email, user.email)
               && Objects.equals(password, user.password)
               && userLevel.equals(user.userLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, userLevel);
    }
}
