package org.example.banking;

import org.example.banking.domain.Account;
import org.example.banking.domain.AccountCreatedEvent;
import org.example.banking.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountShould {
    @Test
    void createAccountCreateEvent_whenCreated() {
        // GIVEN
        UUID customerId = UUID.randomUUID();
        String customerName = "cust";
        Customer customer = new Customer(customerId, customerName);

        UUID accountId = UUID.randomUUID();

        // WHEN
        Account account = new Account(accountId, customer);

        // THEN
        AccountCreatedEvent expectedEvent = new AccountCreatedEvent(accountId, customerId);
        assertThat(account.getUncommittedChanges()).contains(expectedEvent);
    }
}
