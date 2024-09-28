package com.dogadopt.dog_adopt.domain.enums.dog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HealthStatus {

    EXCELLENT("Excellent", "No health issues, active and healthy.", "Kiváló", "Nincsenek egészségügyi problémák, aktív és egészséges."),
    GOOD("Good", "Minor health issues, generally active.", "Jó", "Kisebb egészségügyi problémák, általában aktív."),
    FAIR("Fair", "Moderate health issues, requires some care.", "Közepes", "Mérsékelt egészségügyi problémák, némi gondozást igényel."),
    POOR("Poor", "Serious health issues, needs immediate attention.", "Rossz", "Súlyos egészségügyi problémák, azonnali figyelmet igényel."),
    UNKNOWN("Unknown", "Health status not assessed yet.", "Ismeretlen", "Az egészségi állapotot még nem értékelték.");

    private final String statusNameEn;
    private final String descriptionEn;
    private final String statusNameHu;
    private final String descriptionHu;
}
