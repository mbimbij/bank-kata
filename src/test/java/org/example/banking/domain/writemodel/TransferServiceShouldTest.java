package org.example.banking.domain.writemodel;

import org.assertj.core.api.SoftAssertions;
import org.example.banking.domain.event.MoneyTransferredInEvent;
import org.example.banking.domain.event.MoneyTransferredOutEvent;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class TransferServiceShouldTest {
    @Test
    void create2Events_transferIn_transferOut_whenTransferMoney() {
        // GIVEN
        UUID sourceAccountId = UUID.randomUUID();
        UUID destinationAccountId = UUID.randomUUID();
        Account source = new Account(sourceAccountId, new Customer(UUID.randomUUID(), "customer"));
        Account destination = new Account(destinationAccountId, new Customer(UUID.randomUUID(), "other customer"));

        source.deposit(Money.of(100, "EUR"));

        TransferService transferService = new TransferService();

        // WHEN
        Money transferAmount = Money.of(30, "EUR");
        transferService.transfer(source, destination, transferAmount);

        // THEN
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(source.getUncommittedChanges()).contains(new MoneyTransferredOutEvent(sourceAccountId, destinationAccountId, transferAmount));
            softAssertions.assertThat(source.getBalance()).isEqualTo(Money.of(70, "EUR"));
            softAssertions.assertThat(destination.getUncommittedChanges()).contains(new MoneyTransferredInEvent(destinationAccountId, sourceAccountId, transferAmount));
            softAssertions.assertThat(destination.getBalance()).isEqualTo(Money.of(30, "EUR"));
        });
    }
}
