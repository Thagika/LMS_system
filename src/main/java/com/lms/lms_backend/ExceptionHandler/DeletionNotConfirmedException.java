package com.lms.lms_backend.ExceptionHandler;


public class DeletionNotConfirmedException extends RuntimeException {
  public DeletionNotConfirmedException(String message) {
    super(message);
  }
}