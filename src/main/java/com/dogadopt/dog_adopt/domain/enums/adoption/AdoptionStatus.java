package com.dogadopt.dog_adopt.domain.enums.adoption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdoptionStatus {

    INITIATED("initiated", "kezdeményezett"),
    PENDING("pending", "függőben"),
    FULFILLED("fulfilled", "teljesítve"),
    CANCELED("canceled", "törölve"),
    RETURNED("returned", "visszahozva");

    private final String englishName;
    private final String hungarianName;
}
