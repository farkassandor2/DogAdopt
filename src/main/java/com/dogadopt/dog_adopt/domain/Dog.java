package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.AdoptionType;
import com.dogadopt.dog_adopt.domain.enums.dog.DogBreed;
import com.dogadopt.dog_adopt.domain.enums.dog.DogSize;
import com.dogadopt.dog_adopt.domain.enums.dog.DonationGoal;
import com.dogadopt.dog_adopt.domain.enums.dog.HealthStatus;
import jakarta.persistence.*;
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
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(max = 50, message = "Dog name can be maximum {max} characters.")
    private String name;

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

    private int numberOfVirtualAdopters;

    private int donationReceived;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "dog")
    private List<DogAndUser> dogAndUser;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.donationReceived = 0;
        this.numberOfVirtualAdopters = 0;
    }
}
