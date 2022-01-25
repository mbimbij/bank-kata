package org.example.banking.domain.event;

import org.example.banking.domain.readmodel.AccountStatementsReadRepo;

public class MoneyTransferredOutEventHandler extends AEventHandler<MoneyTransferredOutEvent> {
  public MoneyTransferredOutEventHandler(AccountStatementsReadRepo readRepo) {
    super(readRepo);
  }

  @Override
  public void handle(MoneyTransferredOutEvent domainEvent) {
    readRepo.handle(domainEvent);
  }
}
