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
public class AdoptionInfo {

    private Long id;

    private Long userId;

    private DogInfoOneDog dogInfo;

    private AdoptionType adoptionType;

    private AdoptionStatus adoptionStatus;

    private LocalDateTime createdAt;

}
