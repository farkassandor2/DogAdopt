package com.dogadopt.dog_adopt.dto.outgoing;

import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionStatus;
import com.dogadopt.dog_adopt.domain.enums.adoption.AdoptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DogAndUserAdoptionInfo {

    private Long id;

    private Long userId;

    private Long dogId;

    private AdoptionType adoptionType;

    private AdoptionStatus adoptionStatus;

    private LocalDateTime createdAt;

}
