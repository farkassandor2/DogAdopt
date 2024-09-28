package com.dogadopt.dog_adopt.validation.zip;

import com.dogadopt.dog_adopt.domain.Address;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class ZipValidator implements ConstraintValidator<Zip, String> {

    private static final String US_ZIP_REGEX = "^\\d{5}(-\\d{4})?$"; // United States: 12345 or 12345-6789
    private static final String CANADA_ZIP_REGEX = "^[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d$"; // Canada: A1A 1A1
    private static final String UK_ZIP_REGEX = "^(GIR 0AA|[A-Z]{1,2}\\d{1,2}[A-Z]? ?\\d[A-Z]{2})$"; // United Kingdom
    private static final String GERMANY_ZIP_REGEX = "^\\d{5}$"; // Germany: 12345
    private static final String FRANCE_ZIP_REGEX = "^\\d{5}$"; // France: 75001
    private static final String AUSTRALIA_ZIP_REGEX = "^\\d{4}$"; // Australia: 2000
    private static final String INDIA_ZIP_REGEX = "^\\d{6}$"; // India: 110001
    private static final String JAPAN_ZIP_REGEX = "^\\d{3}-\\d{4}$"; // Japan: 123-4567
    private static final String CHINA_ZIP_REGEX = "^\\d{6}$"; // China: 123456
    private static final String ISRAEL_ZIP_REGEX = "^\\d{7}$"; // Israel: 1234567
    private static final String TURKEY_ZIP_REGEX = "^\\d{5}$"; // Turkey: 34000
    private static final String EGYPT_ZIP_REGEX = "^\\d{5}$"; // Egypt: 12345

    private static final String EUROPEAN_ZIP_REGEX =
            "^\\d{4}$|^\\d{5}$|^[A-Z]{1,2}\\d{1,2}[A-Z]? ?\\d[A-Z]{2}$|^\\d{5}-\\d{3}$";

    private static final String SOUTH_AMERICAN_ZIP_REGEX =
            "^\\d{4}(-\\d{3})?$|^[A-Z]{1}\\d{4}[A-Z]{3}$|^\\d{6}$";

    @Override
    public void initialize(Zip annotation) {
        // No initialization required
    }

    @Override
    public boolean isValid(String zipCode, ConstraintValidatorContext context) {
        if (zipCode == null || zipCode.isEmpty()) {
            return false;
        }

        String countryCode = getCountryCode(context);

        String regex = getRegexForCountry(countryCode);

        return Pattern.matches(regex, zipCode);
    }

    private String getCountryCode(ConstraintValidatorContext context) {
        try {
            Field field = context.getClass().getDeclaredField("rootBean");
            field.setAccessible(true);
            Object obj = field.get(context);

            if (obj instanceof Address address) {
                return address.getCountry().getCountryCode();
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ValidationException("Unable to retrieve Address object from context", e);
        }
        throw new ValidationException("Expected an instance of Address.");
    }

    private String getRegexForCountry(String countryCode) {
        switch (countryCode.toUpperCase()) {
            case "US": return US_ZIP_REGEX;
            case "CA": return CANADA_ZIP_REGEX;
            case "GB": return UK_ZIP_REGEX;
            case "DE": return GERMANY_ZIP_REGEX;
            case "FR": return FRANCE_ZIP_REGEX;
            case "AU": return AUSTRALIA_ZIP_REGEX;
            case "IN": return INDIA_ZIP_REGEX;
            case "JP": return JAPAN_ZIP_REGEX;
            case "CN": return CHINA_ZIP_REGEX;
            case "IL": return ISRAEL_ZIP_REGEX;
            case "TR": return TURKEY_ZIP_REGEX;
            case "EG": return EGYPT_ZIP_REGEX;
            default:
                if (isEuropeanCountry(countryCode)) {
                    return EUROPEAN_ZIP_REGEX;
                } else if (isSouthAmericanCountry(countryCode)) {
                    return SOUTH_AMERICAN_ZIP_REGEX;
                } else {
                    throw new ValidationException("Unsupported country code: " + countryCode);
                }
        }
    }

    private boolean isEuropeanCountry(String countryCode) {
        return switch (countryCode.toUpperCase()) {
            case "AT", "BE", "BG", "HR", "CZ", "DK", "EE", "FI", "GR", "HU", "IS",
                 "IE", "IT", "NL", "NO", "PL", "PT", "RO", "SK", "ES", "SE", "CH" -> true;
            default -> false;
        };
    }

    private boolean isSouthAmericanCountry(String countryCode) {
        return switch (countryCode.toUpperCase()) {
            case "AR", "BR", "CL", "CO", "EC", "PY", "PE", "UY" -> true;
            default -> false;
        };
    }
}