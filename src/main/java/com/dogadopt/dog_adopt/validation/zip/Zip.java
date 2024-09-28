package com.dogadopt.dog_adopt.validation.zip;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ZipValidator.class)
public @interface Zip {

    String message() default "The score is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
