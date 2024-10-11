package com.dogadopt.dog_adopt.exception;

public class IncorrectUsernameOrPasswordException extends RuntimeException {

    public IncorrectUsernameOrPasswordException(String message) {
        super(message);
    }
}
