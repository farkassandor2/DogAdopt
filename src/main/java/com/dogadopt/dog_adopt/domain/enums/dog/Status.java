package com.dogadopt.dog_adopt.domain.enums.dog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    AVAILABLE("Available", "Elérhető"),
    PENDING("Pending", "Függőben"),
    RESERVED("Reserved", "Fenntartva"),
    ADOPTED("Adopted", "Örökbefogadva"),
    FOSTERED("Fostered", "Ideiglenes befogadásban"),
    NOT_AVAILABLE("Not Available", "Nem elérhető"),
    IN_TREATMENT("In Treatment", "Kezelés alatt"),
    DECEASED("Deceased", "Elhunyt"),
    RETURNED("Returned", "Visszahozva"),
    LOST("Lost", "Eltűnt"),
    TRANSFERRED("Transferred", "Áthelyezve");

    private final String adoptionStatusInEnglish;
    private final String adoptionStatusInHungarian;

}
