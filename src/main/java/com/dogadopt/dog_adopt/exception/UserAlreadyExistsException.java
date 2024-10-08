package com.dogadopt.dog_adopt.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super("Customer with e-mail: " + email + " already exists!");
    }
}
