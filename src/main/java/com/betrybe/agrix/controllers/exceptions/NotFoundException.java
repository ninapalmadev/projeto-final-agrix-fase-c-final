package com.betrybe.agrix.controllers.exceptions;

/**
 * Not found exception class.
 */
public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}