package org.example.banking.application;

import lombok.RequiredArgsConstructor;
import org.example.banking.adapter.out.InMemoryAccountStatementsReadRepo;
import org.example.banking.adapter.out.InMemoryCustomerRepository;
import org.example.banking.adapter.out.InMemoryEventStore;
import org.example.banking.domain.event.*;
import org.example.banking.domain.readmodel.AccountStatements;
import org.example.banking.domain.readmodel.AccountStatementsReadRepo;
import org.example.banking.domain.writemodel.AccountRepository;
import org.example.banking.domain.writemodel.Customer;
import org.example.banking.domain.writemodel.CustomerRepository;
import org.example.banking.domain.writemodel.DomainEventPublisher;

import java.util.UUID;

@RequiredArgsConstructor
public class ApplicationFacade {
    private static CustomerRepository customerRepository;
    private final AccountStatementsReadRepo accountStatementsReadRepo;
    private final CommandBus commandBus;

    public AccountStatements getAccountStatements(UUID accountId) {
        return accountStatementsReadRepo.getAccountStatements(accountId);
    }

    public void createCustomer(UUID customerId, String customerName) {
        customerRepository.save(new Customer(customerId, customerName));
    }

    public void createAccount(CreateAccountCommand command) {
        commandBus.send(command);
    }

    public void deposit(DepositMoneyCommand command) {
        commandBus.send(command);
    }

    public void withdraw(WithdrawMoneyCommand command) {
        commandBus.send(command);
    }

    public void tranfer(TransferMoneyCommand command) {
        commandBus.send(command);
    }

    public static ApplicationFacade inMemoryApplication() {
        InMemoryAccountStatementsReadRepo readRepo = new InMemoryAccountStatementsReadRepo();


        DomainEventPublisher domainEventPublisher = new DomainEventPublisher();
        domainEventPublisher.register(new AccountCreatedEventHandler(readRepo));
        domainEventPublisher.register(new MoneyDeposittedEventHandler(readRepo));
        domainEventPublisher.register(new MoneyWithdrawnEventHandler(readRepo));
        domainEventPublisher.register(new MoneyTransferredOutEventHandler(readRepo));
        domainEventPublisher.register(new MoneyTransferredInEventHandler(readRepo));

        InMemoryEventStore eventStore = new InMemoryEventStore();
        AccountRepository accountRepository = new AccountRepository(eventStore, domainEventPublisher);
        customerRepository = new InMemoryCustomerRepository();

        CommandBus commandBus = new CommandBus();
        commandBus.register(new CreateAccountCommandHandler(customerRepository, accountRepository));
        commandBus.register(new DepositMoneyCommandHandler(accountRepository));
        commandBus.register(new WithdrawMoneyCommandHandler(accountRepository));
        commandBus.register(new TransferMoneyCommandHandler(accountRepository));

        return new ApplicationFacade(readRepo, commandBus);
    }
}
