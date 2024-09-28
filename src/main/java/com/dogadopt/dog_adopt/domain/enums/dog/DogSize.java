package com.dogadopt.dog_adopt.domain.enums.dog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DogSize {
    TOY("Toy", "Up to 4.5 kg, Up to 30 cm"),
    SMALL("Small", "4.5 to 11.3 kg, 30 to 41 cm"),
    MEDIUM("Medium", "11.3 to 22.7 kg, 41 to 61 cm"),
    LARGE("Large", "22.7 to 40.8 kg, 61 to 71 cm"),
    GIANT("Giant", "Over 40.8 kg, Over 71 cm");

    private final String sizeName;
    private final String sizeDescription;
}

