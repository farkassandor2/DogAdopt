package com.dogadopt.dog_adopt.dto.outgoing;

import com.dogadopt.dog_adopt.domain.Comment;
import com.dogadopt.dog_adopt.domain.WalkingReservation;
import com.dogadopt.dog_adopt.domain.enums.dog.*;
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

    private Long id;

    private String name;

    private Integer age;

    private DogBreed breed;

    private DogSize dogSize;

    private HealthStatus healthStatus;

    private Status status;

    private String description;

    private List<ImageInfo> imageInfos;

    private Long shelterId;

    private DonationGoal donationGoal;

    private LocalDateTime takenToAdoptionCenter;

    private List<CommentInfo> commentInfos;

    private List<WalkingReservationInfo> walkingReservationInfos;
}
