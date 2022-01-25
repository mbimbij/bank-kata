package org.example.banking.domain.readmodel;

import org.example.banking.domain.event.*;

import java.util.UUID;

public interface AccountStatementsReadRepo {
  AccountStatements getAccountStatements(UUID accountId);

  void handle(AccountCreatedEvent event);

  void handle(MoneyDeposittedEvent event);

  void handle(MoneyWithdrawnEvent event);

  void handle(MoneyTransferredOutEvent event);

  void handle(MoneyTransferredInEvent event);
}
