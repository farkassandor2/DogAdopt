package com.dogadopt.dog_adopt.dto.outgoing;

import com.dogadopt.dog_adopt.domain.Comment;
import com.dogadopt.dog_adopt.domain.WalkingReservation;
import com.dogadopt.dog_adopt.domain.enums.dog.DogBreed;
import com.dogadopt.dog_adopt.domain.enums.dog.DogSize;
import com.dogadopt.dog_adopt.domain.enums.dog.DonationGoal;
import com.dogadopt.dog_adopt.domain.enums.dog.HealthStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DogInfoOneDog {

    private String name;

    private Integer age;

    private DogBreed breed;

    private DogSize dogSize;

    private HealthStatus healthStatus;

    private String description;

    private List<String> imageUrls;

    private String nameOfShelter;

    private DonationGoal donationGoal;

    private LocalDateTime takenToAdoptionCenter;

    private int donationsTotal;

    private List<Comment> comments;

    private List<WalkingReservation> walkingReservations;
}
