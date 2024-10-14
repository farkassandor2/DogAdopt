package com.dogadopt.dog_adopt.repository;

import com.dogadopt.dog_adopt.domain.WalkingReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkingReservationRepository extends JpaRepository<WalkingReservation, Long> {

}
