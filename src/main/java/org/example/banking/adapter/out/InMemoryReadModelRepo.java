package org.example.banking.adapter.out;

import org.example.banking.domain.readmodel.AccountStatements;
import org.example.banking.domain.readmodel.ReadModelRepo;

import java.util.UUID;

public class InMemoryReadModelRepo implements ReadModelRepo {
    @Override
    public AccountStatements getAccountStatements(UUID accountId) {
        return null;
    }
}
