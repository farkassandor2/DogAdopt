package com.dogadopt.dog_adopt.exception;

public class TokenHasAlreadyBeenConfirmed extends RuntimeException {

    public TokenHasAlreadyBeenConfirmed(String token) {
        super(token + " token has already been confirmed.");
    }
}
