package com.dogadopt.dog_adopt.exception;

public class ReservationDoesNotBelongToUserException extends RuntimeException {

    public ReservationDoesNotBelongToUserException(String message) {
        super(message);
    }
}
