package org.example.banking.domain.writemodel;

import org.assertj.core.api.SoftAssertions;
import org.example.banking.domain.event.AccountCreatedEvent;
import org.example.banking.domain.event.MoneyDeposittedEvent;
import org.example.banking.domain.event.MoneyWithdrawnEvent;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    private final String customerName = "cust";
    private final ZonedDateTime creationTimestamp = ZonedDateTime.now();
    private UUID customerId;
    private UUID accountId;
    private Account account;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        accountId = UUID.randomUUID();
        account = new Account(accountId, new Customer(customerId, customerName), creationTimestamp);
    }

    @Test
    void createAccountCreatedEvent_whenCreated() {
        // WHEN account created in setup

        // THEN
        AccountCreatedEvent expectedEvent = new AccountCreatedEvent(accountId, customerId, creationTimestamp);
        assertThat(account.getUncommittedChanges()).contains(expectedEvent);
    }

    @Test
    void createMoneyDeposittedEvent_whenDeposit() {
        // GIVEN
        MonetaryAmount depositAmount = Money.of(10, "EUR");
        ZonedDateTime depositTimestamp = ZonedDateTime.now();

        // WHEN
        account.deposit(depositAmount, depositTimestamp);

        // THEN
        MoneyDeposittedEvent expectedEvent = new MoneyDeposittedEvent(accountId, depositAmount, depositTimestamp);
        Money expectedBalance = Money.of(10, "EUR");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(account.getUncommittedChanges()).contains(expectedEvent);
            softAssertions.assertThat(account.getBalance()).isEqualTo(expectedBalance);
        });
    }

    @Test
    void createMoneyWithdrawnEvent_whenWithdraw() {
        // GIVEN
        account.deposit(Money.of(100, "EUR"), ZonedDateTime.now());

        ZonedDateTime withdrawalTimestamp = ZonedDateTime.now();
        MonetaryAmount withdrawalAmount = Money.of(20, "EUR");

        // WHEN
        account.withdraw(withdrawalAmount, withdrawalTimestamp);

        // THEN
        MoneyWithdrawnEvent expectedEvent = new MoneyWithdrawnEvent(accountId, withdrawalAmount, withdrawalTimestamp);
        Money expectedBalance = Money.of(80, "EUR");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(account.getUncommittedChanges()).contains(expectedEvent);
            softAssertions.assertThat(account.getBalance()).isEqualTo(expectedBalance);
        });
    }
}
