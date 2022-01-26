package org.example.banking.application;

import org.example.banking.domain.readmodel.AccountStatementLineWithBalance;
import org.example.banking.domain.readmodel.AccountStatements;
import org.example.banking.domain.writemodel.AccountDoesNotExistException;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ApplicationFacadeTest {

    private ApplicationFacade applicationFacade;
    private UUID accountId;
    private ZonedDateTime creationTimestamp = ZonedDateTime.now();

    @BeforeEach
    void setUp() {
        applicationFacade = ApplicationFacade.inMemoryApplication();
        accountId = UUID.randomUUID();

        UUID customerId = UUID.randomUUID();
        applicationFacade.createCustomer(customerId, "customer name");

        applicationFacade.createAccount(new CreateAccountCommand(accountId, customerId, creationTimestamp));
    }

    @Test
    void applicationShouldThrownException_whenNoAccountCreated() {
        // GIVEN
        UUID anotherAccountId = UUID.randomUUID();

        // WHEN - THEN
        assertThatThrownBy(() -> applicationFacade.getAccountStatements(anotherAccountId)).isInstanceOf(AccountDoesNotExistException.class);
    }

    @Test
    void applicationShouldReturnEmptyAccountStatement_whenAccountJustCreated() {
        // GIVEN account created in setup

        // WHEN
        AccountStatements accountStatements = applicationFacade.getAccountStatements(accountId);

        // THEN
        AccountStatements expectedAccountStatements = new AccountStatements();
        assertThat(accountStatements).isEqualTo(expectedAccountStatements);
    }

    @Test
    void applicationShouldReturnCorrectAccountStatement_whenDeposit() {
        // GIVEN
        ZonedDateTime depositTimestamp = creationTimestamp.plusDays(1);
        Money eur1000 = Money.of(1000, "EUR");
        applicationFacade.deposit(new DepositMoneyCommand(accountId, eur1000, depositTimestamp));

        // WHEN
        AccountStatements accountStatements = applicationFacade.getAccountStatements(accountId);

        // THEN
        AccountStatements expectedAccountStatements = AccountStatements.fromStatementLinesWithBalance(
                eur1000,
                List.of(
                        new AccountStatementLineWithBalance(depositTimestamp.toLocalDate(), eur1000, null, eur1000)
                ));
        assertThat(accountStatements).isEqualTo(expectedAccountStatements);
    }
}
