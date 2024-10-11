package com.dogadopt.dog_adopt.exception;

public class DogIsAlreadyOnFavoriteListOfUserException extends RuntimeException {

    public DogIsAlreadyOnFavoriteListOfUserException(String message) {
        super(message);
    }
}
