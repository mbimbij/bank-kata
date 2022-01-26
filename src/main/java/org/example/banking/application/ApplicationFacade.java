package org.example.banking.application;

import lombok.RequiredArgsConstructor;
import org.example.banking.domain.readmodel.AccountStatements;
import org.example.banking.domain.readmodel.AccountStatementsReadRepo;

import java.util.UUID;

@RequiredArgsConstructor
public class ApplicationFacade {
    private final AccountStatementsReadRepo accountStatementsReadRepo;

    public AccountStatements getAccountStatements(UUID accountId) {
        return accountStatementsReadRepo.getAccountStatements(accountId);
    }
}
