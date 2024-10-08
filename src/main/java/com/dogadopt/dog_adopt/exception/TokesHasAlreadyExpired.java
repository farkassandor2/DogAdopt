package com.dogadopt.dog_adopt.exception;

public class TokesHasAlreadyExpired extends RuntimeException {

    public TokesHasAlreadyExpired(String token) {
        super(token + " has already expired");
    }
}
