package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.dog.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(max = 50, message = "Dog name can be maximum {max} characters.")
    private String name;

    private LocalDate dateOfBirth;

    @NonNull
    private Integer age;

    @NonNull
    @Enumerated(EnumType.STRING)
    private DogBreed breed;

    @NonNull
    @Enumerated(EnumType.STRING)
    private DogSize dogSize;

    @NonNull
    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Size(max = 500, message = "Description can have maximum {max} characters.")
    private String description;

    @OneToMany(mappedBy = "dog")
    private List<Image> images;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "shelter-id")
    private Shelter shelter;

    @Enumerated(EnumType.STRING)
    private DonationGoal donationGoal;

    private LocalDateTime takenToAdoptionCenter;

    @OneToMany(mappedBy = "dog")
    private List<DogAndUserAdoption> dogAndUserAdoptions;

    @OneToMany(mappedBy = "dog")
    private List<DogAndUserFavorite> dogAndUserFavorites;

    @OneToMany(mappedBy = "dog")
    private List<Donation> donations;

    @OneToMany(mappedBy = "dog")
    private List<Comment> comments;

    @OneToMany(mappedBy = "dog")
    private List<WalkingReservation> walkingReservations;

    @PrePersist
    protected void onCreate() {
        this.takenToAdoptionCenter = LocalDateTime.now();
        this.donationGoal = DonationGoal.GENERAL;
        this.status = Status.AVAILABLE;
    }
}
