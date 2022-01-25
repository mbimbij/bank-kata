package org.example.banking.domain.event;

import org.example.banking.domain.readmodel.AccountStatementsReadRepo;

public class MoneyDeposittedEventHandler extends AEventHandler<MoneyDeposittedEvent> {
    public MoneyDeposittedEventHandler(AccountStatementsReadRepo readRepo) {
        super(readRepo);
    }

    @Override
    public void handle(MoneyDeposittedEvent domainEvent) {
        readRepo.handle(domainEvent);
    }
}
