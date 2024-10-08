package com.dogadopt.dog_adopt.exception;

public class CustomerAlreadyExistsException extends RuntimeException {

    public CustomerAlreadyExistsException(String email) {
        super("Customer with e-mail: " + email + " already exists!");
    }
}
