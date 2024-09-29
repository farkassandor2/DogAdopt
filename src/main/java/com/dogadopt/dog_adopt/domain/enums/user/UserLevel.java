package com.dogadopt.dog_adopt.domain.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserLevel {
    PUPPY_IN_THE_PACK("Puppy in the Pack", "Kölyök a Csapatban", "User has registered but has not yet donated or adopted.", 0),
    KIND_HEART("Kind Heart", "Jószívű", "For users who have donated or adopted a small amount.", 1),
    GENEROUS_SUPPORTER("Generous Supporter", "Nagylelkű Támogató", "For users who have shown more significant support.", 3),
    COMPASSION_CHAMPION("Compassion Champion", "Együttérző Bajnok", "For those who have contributed even more.", 5),
    GUARDIAN_ANGEL("Guardian Angel", "Őrangyal", "For users with substantial contributions.", 7),
    SHELTER_SAVIOR("Shelter Savior", "Menhely Megmentő", "Top-tier support for shelter and dogs.", 10),
    ULTIMATE_PET_HERO("Ultimate Pet Hero", "Állatok Hőse", "The highest level of support.", 15);

    private final String englishName;
    private final String hungarianName;
    private final String description;
    private final int threshold; // This could represent the number of adoptions/donations.
}
