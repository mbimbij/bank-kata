package org.example.banking.domain.event;

import org.example.banking.domain.readmodel.AccountStatementsReadRepo;

public class MoneyTransferredInEventHandler extends AEventHandler<MoneyTransferredInEvent> {
  public MoneyTransferredInEventHandler(AccountStatementsReadRepo readRepo) {
    super(readRepo);
  }

  @Override
  public void handle(MoneyTransferredInEvent domainEvent) {
    readRepo.handle(domainEvent);
  }
}
