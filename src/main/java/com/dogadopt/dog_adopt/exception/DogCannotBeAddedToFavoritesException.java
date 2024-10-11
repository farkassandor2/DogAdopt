package com.dogadopt.dog_adopt.exception;

public class DogCannotBeAddedToFavoritesException extends RuntimeException {

    public DogCannotBeAddedToFavoritesException(String message) {
        super(message);
    }
}
