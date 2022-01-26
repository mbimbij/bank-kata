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
    void applicationShouldReturnCorrectAccountStatement_whenMultipleOperationsPerformed() {
        // GIVEN
        UUID otherCustomerId = UUID.randomUUID();
        UUID otherAccountId = UUID.randomUUID();
        applicationFacade.createCustomer(otherCustomerId, "other customer");
        applicationFacade.createAccount(new CreateAccountCommand(otherAccountId, otherCustomerId, creationTimestamp));

        ZonedDateTime depositTimestamp = creationTimestamp.plusDays(1);
        ZonedDateTime withdrawalTimestamp = creationTimestamp.plusDays(2);
        ZonedDateTime transferTimestamp = creationTimestamp.plusDays(3);

        Money depostiAmount = Money.of(1000, "EUR");
        Money withdrawalAmount = Money.of(200, "EUR");
        Money transferAmount = Money.of(300, "EUR");

        applicationFacade.deposit(new DepositMoneyCommand(accountId, depostiAmount, depositTimestamp));
        applicationFacade.withdraw(new WithdrawMoneyCommand(accountId, withdrawalAmount, withdrawalTimestamp));
        applicationFacade.tranfer(new TransferMoneyCommand(accountId, otherAccountId, transferAmount, transferTimestamp));

        // WHEN
        AccountStatements accountStatements = applicationFacade.getAccountStatements(accountId);

        // THEN
        AccountStatements expectedAccountStatements = AccountStatements.fromStatementLinesWithBalance(
                Money.of(500, "EUR"),
                List.of(
                        new AccountStatementLineWithBalance(transferTimestamp.toLocalDate(), null, transferAmount, Money.of(500, "EUR")),
                        new AccountStatementLineWithBalance(withdrawalTimestamp.toLocalDate(), null, withdrawalAmount, Money.of(800, "EUR")),
                        new AccountStatementLineWithBalance(depositTimestamp.toLocalDate(), depostiAmount, null, depostiAmount)
                ));
        assertThat(accountStatements).usingRecursiveComparison().isEqualTo(expectedAccountStatements);
    }
}
