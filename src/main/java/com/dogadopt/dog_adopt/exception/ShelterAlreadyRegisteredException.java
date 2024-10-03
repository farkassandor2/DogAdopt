package com.dogadopt.dog_adopt.exception;

public class ShelterAlreadyRegisteredException extends RuntimeException {
    public ShelterAlreadyRegisteredException(String message) {
        super(message);
    }
}
