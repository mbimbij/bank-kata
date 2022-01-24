package org.example.banking.domain.readmodel;

import org.assertj.core.api.Assertions;
import org.example.banking.adapter.out.InMemoryReadModelRepo;
import org.example.banking.domain.AccountCreatedEvent;
import org.example.banking.domain.DomainEventPublisher;
import org.example.banking.domain.MoneyDeposittedEvent;
import org.example.banking.domain.MoneyWithdrawnEvent;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ReadModelRepoShould {
    @Test
    void getAccountStatement() {
        // GIVEN
        InMemoryReadModelRepo readModelRepo = new InMemoryReadModelRepo();
        DomainEventPublisher domainEventPublisher = new DomainEventPublisher();

        UUID accountId = UUID.randomUUID();
        domainEventPublisher.publish(List.of(
                new AccountCreatedEvent(accountId, UUID.randomUUID()),
                new MoneyDeposittedEvent(accountId, Money.of(1000, "EUR")),
                new MoneyDeposittedEvent(accountId, Money.of(2000, "EUR")),
                new MoneyWithdrawnEvent(accountId, Money.of(500, "EUR"))
        ));

        // WHEN
        AccountStatements accountStatements = readModelRepo.getAccountStatements(accountId);

        // THEN
        AccountStatements expectedAccountStatements = new AccountStatements(List.of(
                new AccountStatement(LocalDate.of(2012, Month.JANUARY, 14), null, Money.of(500, "EUR"), Money.of(2500, "EUR")),
                new AccountStatement(LocalDate.of(2012, Month.JANUARY, 13), null, Money.of(500, "EUR"), Money.of(3000, "EUR")),
                new AccountStatement(LocalDate.of(2012, Month.JANUARY, 10), null, Money.of(500, "EUR"), Money.of(1000, "EUR"))
        ));
        assertThat(accountStatements).isEqualTo(expectedAccountStatements);
    }
}
