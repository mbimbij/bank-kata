package org.example.banking.application;

import org.example.banking.domain.writemodel.Account;
import org.example.banking.domain.writemodel.AccountRepository;

public class WithdrawMoneyCommandHandler extends ACommandHandler<WithdrawMoneyCommand> {
    public WithdrawMoneyCommandHandler(AccountRepository accountRepository) {
        super(accountRepository);
    }

    @Override
    public void handle(WithdrawMoneyCommand domainEvent) {
        Account account = accountRepository.getById(domainEvent.getAccountId());
        account.withdraw(domainEvent.getMonetaryAmount(), domainEvent.getTimestamp());
        accountRepository.save(account);
    }
}
