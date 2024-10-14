package com.dogadopt.dog_adopt.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalkingReservationCreateUpdateCommand {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
