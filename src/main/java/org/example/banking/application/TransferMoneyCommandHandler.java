package org.example.banking.application;

import org.example.banking.domain.writemodel.Account;
import org.example.banking.domain.writemodel.AccountRepository;
import org.example.banking.domain.writemodel.TransferService;

public class TransferMoneyCommandHandler extends ACommandHandler<TransferMoneyCommand> {
    public TransferMoneyCommandHandler(AccountRepository accountRepository) {
        super(accountRepository);
    }

    @Override
    public void handle(TransferMoneyCommand domainEvent) {
        Account sourceAccount = accountRepository.getById(domainEvent.getAccountId());
        Account destinationAccount = accountRepository.getById(domainEvent.getDestinationAccountId());
        TransferService.transfer(sourceAccount, destinationAccount, domainEvent.getTransferAmount(), domainEvent.getTimestamp());
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
    }
}
