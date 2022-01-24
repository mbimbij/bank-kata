package org.example.banking.domain.readmodel;

import org.example.banking.domain.event.AccountCreatedEvent;
import org.example.banking.domain.event.MoneyDeposittedEvent;
import org.example.banking.domain.event.MoneyWithdrawnEvent;

import java.util.UUID;

public interface AccountStatementsReadRepo {
    AccountStatements getAccountStatements(UUID accountId);

    void handle(AccountCreatedEvent event);


    void handle(MoneyDeposittedEvent event);

    void handle(MoneyWithdrawnEvent event);
}
