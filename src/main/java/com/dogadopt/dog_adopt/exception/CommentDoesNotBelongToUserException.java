package com.dogadopt.dog_adopt.exception;

public class CommentDoesNotBelongToUserException extends RuntimeException {

    public CommentDoesNotBelongToUserException(String message) {
        super(message);
    }
}
