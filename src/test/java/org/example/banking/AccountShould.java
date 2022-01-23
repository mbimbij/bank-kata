package org.example.banking;

import org.example.banking.domain.Account;
import org.example.banking.domain.AccountCreatedEvent;
import org.example.banking.domain.Customer;
import org.example.banking.domain.MoneyDeposittedEvent;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountShould {

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
        assertThat(account.getUncommittedChanges()).contains(expectedEvent);
    }
}
