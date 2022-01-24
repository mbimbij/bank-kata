package org.example.banking.domain.event;

import org.example.banking.domain.readmodel.AccountStatementsReadRepo;

public class MoneyWithdrawnEventHandler extends AEventHandler<MoneyWithdrawnEvent> {
    public MoneyWithdrawnEventHandler(AccountStatementsReadRepo readRepo) {
        super(readRepo);
    }

    @Override
    public void handle(MoneyWithdrawnEvent domainEvent) {

    }
}
