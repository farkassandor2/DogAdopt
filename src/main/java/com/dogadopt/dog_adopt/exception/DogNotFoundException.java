package com.dogadopt.dog_adopt.exception;

public class DogNotFoundException extends RuntimeException {

    public DogNotFoundException(String message) {
        super(message);
    }
}
