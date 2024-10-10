package com.dogadopt.dog_adopt.exception;

public class UserNotActiveException extends RuntimeException {

  public UserNotActiveException(String message) {
    super(message);
  }
}
