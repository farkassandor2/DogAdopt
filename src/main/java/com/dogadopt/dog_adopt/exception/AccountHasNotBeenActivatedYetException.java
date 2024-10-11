package com.dogadopt.dog_adopt.exception;

public class AccountHasNotBeenActivatedYetException extends RuntimeException {

    public AccountHasNotBeenActivatedYetException(String message) {
        super(message);
    }
}
