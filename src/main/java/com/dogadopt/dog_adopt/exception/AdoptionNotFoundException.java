package com.dogadopt.dog_adopt.exception;

public class AdoptionNotFoundException extends RuntimeException {

    public AdoptionNotFoundException(String message) {
        super(message);
    }
}
