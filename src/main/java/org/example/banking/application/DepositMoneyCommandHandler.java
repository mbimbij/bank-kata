package org.example.banking.application;

import org.example.banking.domain.writemodel.Account;
import org.example.banking.domain.writemodel.AccountRepository;

public class DepositMoneyCommandHandler extends ACommandHandler<DepositMoneyCommand> {
  public DepositMoneyCommandHandler(AccountRepository accountRepository) {
    super(accountRepository);
  }

  @Override
  public void handle(DepositMoneyCommand domainEvent) {
    Account account = accountRepository.getById(domainEvent.getAccountId());
  }
}
