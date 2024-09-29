package com.dogadopt.dog_adopt.domain.enums.adoption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdoptionType {
    NONE("None", "Nincs", "No adoption involved."),
    REAL("Real", "Valós", "Physical adoption where the user takes the dog home."),
    VIRTUAL("Virtual", "Virtuális", "Virtual adoption where the user supports the dog financially.");

    private final String englishName;
    private final String hungarianName;
    private final String description;
}
