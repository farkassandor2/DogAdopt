package com.dogadopt.dog_adopt.dto.outgoing;

import com.dogadopt.dog_adopt.domain.enums.reservation.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalkingReservationInfo {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private ReservationStatus reservationStatus;

    private Long userId;

    private DogInfoOneDog dogInfoOneDog;

}
