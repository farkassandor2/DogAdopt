package com.dogadopt.dog_adopt.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DogAndUserFavoriteInfo {

    private Long id;

    private DogInfoOneDog dogInfo;

    private LocalDateTime createdAt;
}
