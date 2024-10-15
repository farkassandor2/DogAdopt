package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.WalkingReservation;
import com.dogadopt.dog_adopt.domain.enums.reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface WalkingReservationRepository extends JpaRepository<WalkingReservation, Long> {

    @Query("SELECT CASE WHEN COUNT(wr) > 0 " +
           "THEN TRUE ELSE FALSE END " +
           "FROM WalkingReservation wr " +
           "WHERE wr.dog = ?1 " +
           "AND wr.startTime = ?2 " +
           "AND wr.endTime = ?3")
    boolean checkIfTimeWindowForWalkAlreadyTaken(Dog dog, LocalDateTime start, LocalDateTime end);

    @Modifying
    @Query("UPDATE WalkingReservation wr " +
           "SET wr.reservationStatus = 'CANCELLED' " +
           "WHERE wr.user = ?1 " +
           "AND wr.id = ?2")
    void deleteReservationOfUserByUser(AppUser user, Long reservationId);

    @Query("SELECT CASE WHEN COUNT(wr) > 0 " +
           "THEN TRUE ELSE FALSE END " +
           "FROM WalkingReservation wr " +
           "WHERE wr.user = ?1 " +
           "AND wr.reservationStatus = ?2")
    boolean checkIfUserAlreadyHasActiveReservedWalking(AppUser user, ReservationStatus status);

    @Modifying
    @Query("UPDATE WalkingReservation wr " +
           "SET wr.reservationStatus = 'CANCELLED' "  +
           "WHERE wr.id = ?1")
    void deleteReservationOfUserByAdmin(Long reservationId);

    @Query("SELECT wr " +
           "FROM WalkingReservation wr " +
           "WHERE wr.user.id = ?1")
    List<WalkingReservation> findReservationByUser(Long userId);

    @Query("SELECT wr " +
           "FROM WalkingReservation wr " +
           "WHERE wr.dog.id = ?1")
    List<WalkingReservation> findReservationByDog(Long dogId);

    @Query("SELECT wr " +
           "FROM WalkingReservation wr " +
           "WHERE wr.endTime < ?1")
    Stream<WalkingReservation> streamAllReservation(LocalDateTime time);
}
