package com.dogadopt.dog_adopt.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String email) {
    super("User not found with e-mail " + email);
  }
}
