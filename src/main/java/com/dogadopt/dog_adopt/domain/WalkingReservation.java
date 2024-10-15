package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.reservation.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "walking_reservation")
public class WalkingReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private LocalDateTime startTime;

    @NonNull
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @PrePersist
    protected void onCreate() {
        this.reservationStatus = ReservationStatus.ACTIVE;
    }
}
