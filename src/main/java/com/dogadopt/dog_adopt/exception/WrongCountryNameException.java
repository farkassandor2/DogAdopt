package com.dogadopt.dog_adopt.exception;

public class WrongCountryNameException extends RuntimeException {

  public WrongCountryNameException(String countryName) {
    super("Country cannot be null or empty or " + countryName + " is in a different way in the system!");
  }
}
