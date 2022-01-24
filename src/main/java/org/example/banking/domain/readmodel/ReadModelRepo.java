package org.example.banking.domain.readmodel;

import java.util.UUID;

public interface ReadModelRepo {
    AccountStatements getAccountStatements(UUID accountId);
}
