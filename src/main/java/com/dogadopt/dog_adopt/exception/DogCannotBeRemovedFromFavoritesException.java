package com.dogadopt.dog_adopt.exception;

public class DogCannotBeRemovedFromFavoritesException extends RuntimeException {

    public DogCannotBeRemovedFromFavoritesException(String message) {
        super(message);
    }
}
