package com.dogadopt.dog_adopt.domain.enums.dog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DonationGoal {

    GENERAL("General Fund", "Általános Gondozás"),
    VETERINARY_CARE("Veterinary Care Fund", "Állatorvosi Gondozási Alap"),
    FOOD_AND_SUPPLIES("Food and Supplies Fund", "Élelmiszer- és Kellék Alap"),
    ADOPTION_EVENTS("Adoption Events Fund", "Örökbefogadási Események Alap"),
    TRAINING_AND_SOCIALIZATION("Training and Socialization Fund", "Képzési és Szocializációs Alap"),
    EMERGENCY_MEDICAL("Emergency Medical Fund", "Sürgősségi Orvosi Alap"),
    RESCUE_TRANSPORT("Rescue Transport Fund", "Mentőszállítási Alap"),
    FOSTER_PROGRAM("Foster Program Support", "Fostering Program Támogatás"),
    SPAY_NEUTER("Spay/Neuter Program", "Ivartalanítási Program"),
    COMMUNITY_OUTREACH("Community Outreach and Education Fund", "Közösségi Elérések és Oktatási Alap"),
    SHELTER_IMPROVEMENT("Shelter Improvement Fund", "Menhely Fejlesztési Alap");

    private final String goalNameEn;
    private final String goalNameHu;
}
