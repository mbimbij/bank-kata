package org.example.banking.application;

import org.example.banking.adapter.out.InMemoryAccountStatementsReadRepo;
import org.example.banking.domain.readmodel.AccountStatements;
import org.example.banking.domain.writemodel.AccountDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ApplicationFacadeTest {

    private ApplicationFacade applicationFacade;
    private UUID accountId;

    @BeforeEach
    void setUp() {
        applicationFacade = ApplicationFacade.inMemoryApplication();
        accountId = UUID.randomUUID();
    }

    @Test
    void applicationShouldThrownException_whenNoAccountCreated() {
        // WHEN - THEN
        assertThatThrownBy(() -> applicationFacade.getAccountStatements(accountId)).isInstanceOf(AccountDoesNotExistException.class);
    }

    @Test
    void applicationShouldReturnEmptyAccountStatement_whenAccountJustCreated() {
        // GIVEN
        UUID customerId = UUID.randomUUID();
        applicationFacade.createCustomer(customerId, "customer name");


        applicationFacade.createAccount(new CreateAccountCommand(accountId, customerId, ZonedDateTime.now()));

        // WHEN
        applicationFacade.getAccountStatements(accountId);

        // THEN
        AccountStatements accountStatements = new AccountStatements();
        applicationFacade.getAccountStatements(accountId);
    }
}
