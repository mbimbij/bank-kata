package org.example.banking.domain.writemodel;

import org.assertj.core.api.SoftAssertions;
import org.example.banking.adapter.out.InMemoryEventStore;
import org.example.banking.domain.event.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

class AccountRepositoryTest {
  @Test
  void accountRepositoryShould_getAccountById() {
    // GIVEN
    UUID accountId = UUID.randomUUID();

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.save(List.of(
        new AccountCreatedEvent(accountId, new Customer(UUID.randomUUID(), "some customer"), ZonedDateTime.now()),
        new MoneyDeposittedEvent(accountId, Money.of(1000, "EUR"), ZonedDateTime.now().plusDays(1)),
        new MoneyWithdrawnEvent(accountId, Money.of(200, "EUR"), ZonedDateTime.now().plusDays(2)),
        new MoneyTransferredOutEvent(accountId, UUID.randomUUID(), Money.of(300, "EUR"), ZonedDateTime.now().plusDays(3)),
        new MoneyTransferredInEvent(accountId, UUID.randomUUID(), Money.of(100, "EUR"), ZonedDateTime.now().plusDays(3))
    ));

    AccountRepository accountRepository = new AccountRepository(eventStore, new DomainEventPublisher());

    // WHEN
    Account account = accountRepository.getById(accountId);

    // THEN
    SoftAssertions.assertSoftly(softAssertions -> {
      softAssertions.assertThat(account.getUncommittedChanges()).isEmpty();
      softAssertions.assertThat(account.getBalance()).isEqualTo(Money.of(600, "EUR"));
    });
  }
}