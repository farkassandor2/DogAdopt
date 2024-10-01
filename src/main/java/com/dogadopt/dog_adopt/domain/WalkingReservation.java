package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.reservation.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class WalkingReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private ReservationStatus reservationStatus;

    @OneToMany(mappedBy = "walkingReservation")
    private List<AppUser> users;

    @OneToMany(mappedBy = "walkingReservation")
    private List<Dog> dogs;
}
