package org.example.banking.domain.writemodel;

import org.assertj.core.api.SoftAssertions;
import org.example.banking.domain.event.MoneyTransferredInEvent;
import org.example.banking.domain.event.MoneyTransferredOutEvent;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

class TransferServiceTest {
    @Test
    void create2Events_transferIn_transferOut_whenTransferMoney() {
        // GIVEN
        UUID sourceAccountId = UUID.randomUUID();
        UUID destinationAccountId = UUID.randomUUID();

        Account source = new Account(sourceAccountId, new Customer(UUID.randomUUID(), "customer"), ZonedDateTime.now());
        Account destination = new Account(destinationAccountId, new Customer(UUID.randomUUID(), "other customer"), ZonedDateTime.now());

        source.deposit(Money.of(100, "EUR"), ZonedDateTime.now());

        ZonedDateTime moneyTransferTimestamp = ZonedDateTime.now();

        // WHEN
        Money transferAmount = Money.of(30, "EUR");
        TransferService.transfer(source, destination, transferAmount, moneyTransferTimestamp);

        // THEN
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(source.getUncommittedChanges()).contains(new MoneyTransferredOutEvent(sourceAccountId, destinationAccountId, transferAmount, moneyTransferTimestamp));
            softAssertions.assertThat(source.getBalance()).isEqualTo(Money.of(70, "EUR"));
            softAssertions.assertThat(destination.getUncommittedChanges()).contains(new MoneyTransferredInEvent(destinationAccountId, sourceAccountId, transferAmount, moneyTransferTimestamp));
            softAssertions.assertThat(destination.getBalance()).isEqualTo(Money.of(30, "EUR"));
        });
    }
}
