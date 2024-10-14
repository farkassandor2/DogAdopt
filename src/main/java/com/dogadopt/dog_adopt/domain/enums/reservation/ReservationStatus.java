package com.dogadopt.dog_adopt.domain.enums.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    ACTIVE("Active", "Aktív"),
    CANCELLED("Cancelled", "Lemondva"),
    FULFILLED("Fulfilled", "Teljesített");

    private final String englishName;
    private final String hungarianName;
}
