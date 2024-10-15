package com.dogadopt.dog_adopt.service.walkingreservation;

import com.dogadopt.dog_adopt.dto.incoming.WalkingReservationCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.WalkingReservationInfo;
import jakarta.validation.Valid;

import java.util.List;

public interface WalkingReservationService {

    WalkingReservationInfo createWalkingReservation(Long userId, Long dogId, @Valid WalkingReservationCreateUpdateCommand command);

    void deleteWalkingReservationByUser(Long userId, Long reservationId);

    void deleteWalkingReservationByAdmin(Long reservationId);

    List<WalkingReservationInfo> getAllReservationsByAdmin();

    List<WalkingReservationInfo> getAllReservationsByAdminForUser(Long userId);

    WalkingReservationInfo changeReservationTime(Long userId, Long reservationId, WalkingReservationCreateUpdateCommand command);

    void setWalkingStatusToFulfilled();

    List<WalkingReservationInfo> getWalkingReservationsByAdminForDog(Long dogId);

    List<WalkingReservationInfo> getWalkingReservationsByUserForUser(Long userId);

    List<WalkingReservationInfo> getWalkingReservationsByUserForDog(Long userId, Long dogId);
}
