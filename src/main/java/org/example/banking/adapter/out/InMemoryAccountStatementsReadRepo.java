package org.example.banking.adapter.out;

import org.example.banking.domain.event.AccountCreatedEvent;
import org.example.banking.domain.event.MoneyDeposittedEvent;
import org.example.banking.domain.event.MoneyWithdrawnEvent;
import org.example.banking.domain.readmodel.AccountStatements;
import org.example.banking.domain.readmodel.AccountStatementsReadRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryAccountStatementsReadRepo implements AccountStatementsReadRepo {
    Map<UUID, AccountStatements> accountStatementList = new HashMap<>();

    @Override
    public AccountStatements getAccountStatements(UUID accountId) {
        return null;
    }

    @Override
    public void handle(AccountCreatedEvent event) {

    }

    @Override
    public void handle(MoneyDeposittedEvent event) {
//        accountStatementList.add(new AccountStatement(event.getTimestamp().toLocalDate(), event.getDeposit(), null,));
    }

    @Override
    public void handle(MoneyWithdrawnEvent event) {

    }
}
