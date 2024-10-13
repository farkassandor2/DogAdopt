package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionStatus;
import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Adoption")
public class DogAndUserAdoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private AdoptionType adoptionType;

    @Enumerated(EnumType.STRING)
    private AdoptionStatus adoptionStatus;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
