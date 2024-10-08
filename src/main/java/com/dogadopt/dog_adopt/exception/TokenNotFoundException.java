package com.dogadopt.dog_adopt.exception;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String token) {
        super(token + " not found!");
    }
}
