package org.example.banking.adapter.out;

import org.example.banking.domain.event.*;
import org.example.banking.domain.readmodel.AccountDoesNotExistException;
import org.example.banking.domain.readmodel.AccountStatementLineWithoutBalance;
import org.example.banking.domain.readmodel.AccountStatements;
import org.example.banking.domain.readmodel.AccountStatementsReadRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryAccountStatementsReadRepo implements AccountStatementsReadRepo {
  Map<UUID, AccountStatements> accountStatementsMap = new HashMap<>();

  @Override
  public AccountStatements getAccountStatements(UUID accountId) {
    return Optional.ofNullable(accountStatementsMap.get(accountId))
        .orElseThrow(() -> new AccountDoesNotExistException(accountId));
  }

  @Override
  public void handle(AccountCreatedEvent event) {
    accountStatementsMap.put(event.getAccountId(), new AccountStatements());
  }

  @Override
  public void handle(MoneyDeposittedEvent event) {
    accountStatementsMap.get(event.getAccountId()).handle(new AccountStatementLineWithoutBalance(event.getTimestamp().toLocalDate(),
        event.getDeposit(),
        null));
  }

  @Override
  public void handle(MoneyWithdrawnEvent event) {
    accountStatementsMap.get(event.getAccountId()).handle(new AccountStatementLineWithoutBalance(event.getTimestamp().toLocalDate(),
        null,
        event.getWithdrawal()));
  }

  @Override
  public void handle(MoneyTransferredOutEvent event) {
    accountStatementsMap.get(event.getAccountId()).handle(new AccountStatementLineWithoutBalance(event.getTimestamp().toLocalDate(),
        null,
        event.getTransferAmount()));
  }

  @Override
  public void handle(MoneyTransferredInEvent event) {
    accountStatementsMap.get(event.getAccountId()).handle(new AccountStatementLineWithoutBalance(event.getTimestamp().toLocalDate(),
        event.getTransferAmount(),
        null));
  }
}
