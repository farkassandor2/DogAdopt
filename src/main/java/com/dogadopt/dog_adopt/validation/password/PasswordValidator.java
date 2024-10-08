package com.dogadopt.dog_adopt.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {


    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.length() < 8 || password.length() > 16) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[!@#$%^&*()].*")) return false;
        if (!password.matches(".*\\d.*")) return false;
        return password.matches(".*[a-z].*");
    }
}
