package org.example.banking.application;

import org.example.banking.adapter.out.InMemoryCustomerRepository;
import org.example.banking.adapter.out.InMemoryEventStore;
import org.example.banking.domain.event.AccountCreatedEvent;
import org.example.banking.domain.event.MoneyDeposittedEvent;
import org.example.banking.domain.writemodel.AccountRepository;
import org.example.banking.domain.writemodel.Customer;
import org.example.banking.domain.writemodel.MockDomainEventPublisher;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CommandBusTest {

  protected CommandBus commandBus;
  protected InMemoryEventStore eventStore;
  protected MockDomainEventPublisher mockDomainEventPublisher;
  protected UUID accountId;
  protected Customer customer;

  @BeforeEach
  void setUp() {
    accountId = UUID.randomUUID();

    InMemoryCustomerRepository customerRepository = new InMemoryCustomerRepository();
    customer = new Customer(UUID.randomUUID(), "customer name");
    customerRepository.save(customer);

    eventStore = new InMemoryEventStore();
    mockDomainEventPublisher = new MockDomainEventPublisher();

    AccountRepository accountRepository = new AccountRepository(eventStore, mockDomainEventPublisher);

    commandBus = new CommandBus();
    commandBus.register(new CreateAccountCommandHandler(customerRepository, accountRepository));
    commandBus.register(new DepositMoneyCommandHandler(accountRepository));
  }

  @Test
  void eventShouldBeSaved_andPublished_whenCreateAccountCommandSent() {
    // GIVEN created in setup
    ZonedDateTime commandTimestamp = ZonedDateTime.now();

    // WHEN
    commandBus.send(new CreateAccountCommand(accountId, customer.getId(), commandTimestamp));

    // THEN
    AccountCreatedEvent expectedEvent = new AccountCreatedEvent(accountId, customer, commandTimestamp);
    assertThat(eventStore.getEvents()).contains(expectedEvent);
    assertThat(mockDomainEventPublisher.getPublishedEvents()).contains(expectedEvent);
  }

  @Test
  void eventShouldBeSaved_andPublished_whenDepositMoneyCommandSent() {
    // GIVEN
    ZonedDateTime accountCreationTimestamp = ZonedDateTime.now();
    ZonedDateTime commandTimestamp = accountCreationTimestamp.plusDays(1);
    eventStore.save(List.of(new AccountCreatedEvent(accountId, customer, accountCreationTimestamp)));

    Money depositAmount = Money.of(100, "EUR");

    // WHEN
    commandBus.send(new DepositMoneyCommand(accountId, depositAmount, commandTimestamp));

    // THEN
    MoneyDeposittedEvent expectedEvent = new MoneyDeposittedEvent(accountId, depositAmount, commandTimestamp);
    assertThat(eventStore.getEvents()).contains(expectedEvent);
    assertThat(mockDomainEventPublisher.getPublishedEvents()).contains(expectedEvent);
  }
}