package org.example.banking.domain.readmodel;

import org.example.banking.adapter.out.InMemoryAccountStatementsReadRepo;
import org.example.banking.domain.event.*;
import org.example.banking.domain.writemodel.Customer;
import org.example.banking.domain.writemodel.DomainEventPublisher;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountStatementsReadRepoTest {
  @Test
  void getCorrectAccountStatementWithAllOperationTypesPerformed() {
    // GIVEN
    InMemoryAccountStatementsReadRepo readRepo = new InMemoryAccountStatementsReadRepo();
    DomainEventPublisher domainEventPublisher = new DomainEventPublisher();
    domainEventPublisher.register(new AccountCreatedEventHandler(readRepo));
    domainEventPublisher.register(new MoneyDeposittedEventHandler(readRepo));
    domainEventPublisher.register(new MoneyWithdrawnEventHandler(readRepo));
    domainEventPublisher.register(new MoneyTransferredOutEventHandler(readRepo));
    domainEventPublisher.register(new MoneyTransferredInEventHandler(readRepo));

    LocalDate january10 = LocalDate.of(2012, Month.JANUARY, 10);
    LocalDate january13 = LocalDate.of(2012, Month.JANUARY, 13);
    LocalDate january14 = LocalDate.of(2012, Month.JANUARY, 14);
    LocalDate january15 = LocalDate.of(2012, Month.JANUARY, 15);
    LocalDate january16 = LocalDate.of(2012, Month.JANUARY, 16);

    UUID accountId1 = UUID.randomUUID();
    UUID accountId2 = UUID.randomUUID();
    domainEventPublisher.publish(List.of(
        new AccountCreatedEvent(accountId1, new Customer(UUID.randomUUID(), "some cutomer"), ZonedDateTime.now()),
        new AccountCreatedEvent(accountId2, new Customer(UUID.randomUUID(), "some other cutomer"), ZonedDateTime.now()),
        new MoneyDeposittedEvent(accountId1, Money.of(1000, "EUR"), january10.atStartOfDay(ZoneId.systemDefault())),
        new MoneyDeposittedEvent(accountId1, Money.of(2000, "EUR"), january13.atStartOfDay(ZoneId.systemDefault())),
        new MoneyWithdrawnEvent(accountId1, Money.of(500, "EUR"), january14.atStartOfDay(ZoneId.systemDefault())),
        new MoneyTransferredOutEvent(accountId1, accountId2, Money.of(700, "EUR"), january15.atStartOfDay(ZoneId.systemDefault())),
        new MoneyTransferredInEvent(accountId2, accountId1, Money.of(700, "EUR"), january15.atStartOfDay(ZoneId.systemDefault())),
        new MoneyTransferredOutEvent(accountId2, accountId1, Money.of(400, "EUR"), january16.atStartOfDay(ZoneId.systemDefault())),
        new MoneyTransferredInEvent(accountId1, accountId2, Money.of(400, "EUR"), january16.atStartOfDay(ZoneId.systemDefault()))
    ));

    // WHEN
    AccountStatements accountStatements = readRepo.getAccountStatements(accountId1);

    // THEN
    AccountStatements expectedAccountStatements = AccountStatements.fromStatementLinesWithBalance(
        Money.of(2200, "EUR"),
        List.of(
            new AccountStatementLineWithBalance(january16, Money.of(400, "EUR"), null, Money.of(2200, "EUR")),
            new AccountStatementLineWithBalance(january15, null, Money.of(700, "EUR"), Money.of(1800, "EUR")),
            new AccountStatementLineWithBalance(january14, null, Money.of(500, "EUR"), Money.of(2500, "EUR")),
            new AccountStatementLineWithBalance(january13, Money.of(2000, "EUR"), null, Money.of(3000, "EUR")),
            new AccountStatementLineWithBalance(january10, Money.of(1000, "EUR"), null, Money.of(1000, "EUR"))
        ));
    assertThat(accountStatements).usingRecursiveComparison().isEqualTo(expectedAccountStatements);
  }
}
