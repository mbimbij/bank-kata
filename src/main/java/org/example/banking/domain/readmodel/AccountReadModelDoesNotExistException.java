package org.example.banking.domain.readmodel;

import java.util.UUID;

public class AccountReadModelDoesNotExistException extends RuntimeException {
    public AccountReadModelDoesNotExistException(UUID accountId) {
        super(accountId.toString());
    }
}
