package org.example.banking.application;

import org.example.banking.domain.writemodel.Account;
import org.example.banking.domain.writemodel.AccountRepository;
import org.example.banking.domain.writemodel.Customer;
import org.example.banking.domain.writemodel.CustomerRepository;

import java.time.ZonedDateTime;
import java.util.UUID;

public class CreateAccountCommandHandler extends ACommandHandler<CreateAccountCommand> {
  private final CustomerRepository customerRepository;

  public CreateAccountCommandHandler(CustomerRepository customerRepository, AccountRepository aggregateRepository) {
    super(aggregateRepository);
    this.customerRepository = customerRepository;
  }

  @Override
  public void handle(CreateAccountCommand domainEvent) {
    UUID newAccountId = domainEvent.getAccountId();
    UUID customerId = domainEvent.getCustomerId();
    ZonedDateTime accountCreationTimestamp = domainEvent.getTimestamp();

    Customer customer = customerRepository.getById(customerId);
    Account newAccount = new Account(newAccountId, customer, accountCreationTimestamp);

    accountRepository.save(newAccount);
  }
}
