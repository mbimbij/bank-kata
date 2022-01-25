package org.example.banking.domain.writemodel;

import java.util.UUID;

public class AccountDoesNotExistException extends RuntimeException {
  public AccountDoesNotExistException(UUID accountId) {
    super(accountId.toString());
  }
}
