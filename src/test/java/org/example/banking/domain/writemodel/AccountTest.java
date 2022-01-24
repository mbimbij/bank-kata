package org.example.banking.domain.writemodel;

import org.assertj.core.api.SoftAssertions;
import org.example.banking.domain.event.AccountCreatedEvent;
import org.example.banking.domain.event.MoneyDeposittedEvent;
import org.example.banking.domain.event.MoneyWithdrawnEvent;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    private final String customerName = "cust";
    private UUID customerId;
    private Customer customer;
    private UUID accountId;
    private Account account;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        customer = new Customer(customerId, customerName);
        accountId = UUID.randomUUID();
        account = new Account(accountId, customer);
    }

    @Test
    void createAccountCreatedEvent_whenCreated() {
        // WHEN account created in setup

        // THEN
        AccountCreatedEvent expectedEvent = new AccountCreatedEvent(accountId, customerId);
        assertThat(account.getUncommittedChanges()).contains(expectedEvent);
    }

    @Test
    void createMoneyDeposittedEvent_whenDeposit() {
        // WHEN
        MonetaryAmount deposit = Money.of(10, "EUR");
        account.deposit(deposit);

        // THEN
        MoneyDeposittedEvent expectedEvent = new MoneyDeposittedEvent(accountId, deposit);
        Money expectedBalance = Money.of(10, "EUR");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(account.getUncommittedChanges()).contains(expectedEvent);
            softAssertions.assertThat(account.getBalance()).isEqualTo(expectedBalance);
        });
    }

    @Test
    void createMoneyWithdrawnEvent_whenWithdraw() {
        // GIVEN
        account.deposit(Money.of(100, "EUR"));

        // WHEN
        MonetaryAmount withdrawal = Money.of(20, "EUR");
        account.withdraw(withdrawal);

        // THEN
        MoneyWithdrawnEvent expectedEvent = new MoneyWithdrawnEvent(accountId, withdrawal);
        Money expectedBalance = Money.of(80, "EUR");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(account.getUncommittedChanges()).contains(expectedEvent);
            softAssertions.assertThat(account.getBalance()).isEqualTo(expectedBalance);
        });
    }
}
