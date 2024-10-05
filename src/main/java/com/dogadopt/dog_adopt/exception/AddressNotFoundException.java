package com.dogadopt.dog_adopt.exception;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(String message) {
        super(message);
    }
}
