package org.example.banking.domain.readmodel;

import org.example.banking.adapter.out.InMemoryAccountStatementsReadRepo;
import org.example.banking.domain.event.AccountCreatedEvent;
import org.example.banking.domain.event.AccountCreatedEventHandler;
import org.example.banking.domain.event.DomainEventPublisher;
import org.example.banking.domain.event.MoneyDeposittedEvent;
import org.example.banking.domain.event.MoneyDeposittedEventHandler;
import org.example.banking.domain.event.MoneyWithdrawnEvent;
import org.example.banking.domain.event.MoneyWithdrawnEventHandler;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountStatementsReadRepoTest {
    @Test
    void getAccountStatement() {
        // GIVEN
        InMemoryAccountStatementsReadRepo readRepo = new InMemoryAccountStatementsReadRepo();
        DomainEventPublisher domainEventPublisher = new DomainEventPublisher();
        AccountCreatedEventHandler accountCreatedEventHandler = new AccountCreatedEventHandler(readRepo);
        MoneyDeposittedEventHandler moneyDeposittedEventHandler = new MoneyDeposittedEventHandler(readRepo);
        MoneyWithdrawnEventHandler moneyWithdrawnEventHandler = new MoneyWithdrawnEventHandler(readRepo);
        domainEventPublisher.register(accountCreatedEventHandler);
        domainEventPublisher.register(moneyDeposittedEventHandler);
        domainEventPublisher.register(moneyWithdrawnEventHandler);

        UUID accountId = UUID.randomUUID();
        domainEventPublisher.publish(List.of(
                new AccountCreatedEvent(accountId, UUID.randomUUID(), ZonedDateTime.now()),
                new MoneyDeposittedEvent(accountId, Money.of(1000, "EUR"), ZonedDateTime.now()),
                new MoneyDeposittedEvent(accountId, Money.of(2000, "EUR"), ZonedDateTime.now()),
                new MoneyWithdrawnEvent(accountId, Money.of(500, "EUR"), ZonedDateTime.now())
        ));

        // WHEN
        AccountStatements accountStatements = readRepo.getAccountStatements(accountId);

        // THEN
//        AccountStatements expectedAccountStatements = new AccountStatements(List.of(
//                new AccountStatementLine(LocalDate.of(2012, Month.JANUARY, 14), null, Money.of(500, "EUR"), Money.of(2500, "EUR")),
//                new AccountStatementLine(LocalDate.of(2012, Month.JANUARY, 13), null, Money.of(500, "EUR"), Money.of(3000, "EUR")),
//                new AccountStatementLine(LocalDate.of(2012, Month.JANUARY, 10), null, Money.of(500, "EUR"), Money.of(1000, "EUR"))
//        ));
//        assertThat(accountStatements).isEqualTo(expectedAccountStatements);
    }
}
