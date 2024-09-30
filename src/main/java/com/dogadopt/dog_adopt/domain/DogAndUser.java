package com.dogadopt.dog_adopt.domain;

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
@Table(name = "Dog and User")
public class DogAndUser {

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

    private LocalDateTime createdAt;

    protected void onCreate() {
        this.adoptionType = AdoptionType.NONE;
        this.createdAt = LocalDateTime.now();
    }
}
