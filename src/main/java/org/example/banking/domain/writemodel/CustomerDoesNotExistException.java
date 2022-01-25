package org.example.banking.domain.writemodel;

public class CustomerDoesNotExistException extends RuntimeException {
  public CustomerDoesNotExistException(String message) {
    super(message);
  }
}
