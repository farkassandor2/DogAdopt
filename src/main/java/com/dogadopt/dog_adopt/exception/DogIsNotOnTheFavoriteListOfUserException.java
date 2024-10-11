package com.dogadopt.dog_adopt.exception;

public class DogIsNotOnTheFavoriteListOfUserException extends RuntimeException {

    public DogIsNotOnTheFavoriteListOfUserException(String message) {
        super(message);
    }
}
