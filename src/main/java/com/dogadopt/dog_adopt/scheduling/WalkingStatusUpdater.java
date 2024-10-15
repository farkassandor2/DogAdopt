package com.dogadopt.dog_adopt.scheduling;

import com.dogadopt.dog_adopt.service.walkingreservation.WalkingReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class WalkingStatusUpdater {

    private final WalkingReservationService walkingReservationService;

    @Scheduled(cron = "0 0 * * * *")
    public void updateWalkingReservationToFulfilled() {
        walkingReservationService.setWalkingStatusToFulfilled();
    }
}
