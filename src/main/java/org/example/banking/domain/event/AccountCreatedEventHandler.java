package org.example.banking.domain.event;

import org.example.banking.domain.readmodel.AccountStatementsReadRepo;

public class AccountCreatedEventHandler extends AEventHandler<AccountCreatedEvent> {
    public AccountCreatedEventHandler(AccountStatementsReadRepo readRepo) {
        super(readRepo);
    }

    @Override
    public void handle(AccountCreatedEvent domainEvent) {
        readRepo.handle(domainEvent);
    }
}
