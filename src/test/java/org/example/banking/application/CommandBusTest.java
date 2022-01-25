package org.example.banking.application;

import org.example.banking.adapter.out.InMemoryCustomerRepository;
import org.example.banking.adapter.out.InMemoryEventStore;
import org.example.banking.domain.event.AccountCreatedEvent;
import org.example.banking.domain.writemodel.AccountRepository;
import org.example.banking.domain.writemodel.Customer;
import org.example.banking.domain.writemodel.CustomerRepository;
import org.example.banking.domain.writemodel.MockDomainEventPublisher;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CommandBusTest {
  @Test
  void eventShouldBeSaved_andPublished_whenCreateAccountCommandSent() {
    // GIVEN
    InMemoryCustomerRepository customerRepository = new InMemoryCustomerRepository();
    UUID customerId = UUID.randomUUID();
    customerRepository.save(new Customer(customerId, "customer name"));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    MockDomainEventPublisher mockDomainEventPublisher = new MockDomainEventPublisher();
    AccountRepository accountRepository = new AccountRepository(eventStore, mockDomainEventPublisher);

    CommandBus commandBus = new CommandBus();
    commandBus.register(new CreateAccountCommandHandler(customerRepository, accountRepository));

    // WHEN
    UUID newAccountId = UUID.randomUUID();
    ZonedDateTime timestamp = ZonedDateTime.now();
    commandBus.send(new CreateAccountCommand(newAccountId, customerId, timestamp));

    // THEN
    AccountCreatedEvent expectedEvent = new AccountCreatedEvent(newAccountId, customerId, timestamp);
    assertThat(eventStore.getEvents()).contains(expectedEvent);
    assertThat(mockDomainEventPublisher.getPublishedEvents()).contains(expectedEvent);
  }
}