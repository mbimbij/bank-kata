package org.example.banking.domain.readmodel;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccountStatementsTest {
    @Test
    void shouldReturnEmptyList_whenNoActivity() {
        // GIVEN
        AccountStatements accountStatements = new AccountStatements();

        // WHEN
        List<AccountStatementLineWithBalance> statementLines = accountStatements.getStatementLines();

        // THEN
        assertThat(statementLines).isEmpty();
    }

    @Test
    void handleASingleDeposit() {
        // GIVEN
        AccountStatements accountStatements = new AccountStatements();

        accountStatements.handle(new AccountStatementLineWithoutBalance(LocalDate.of(2012, Month.JANUARY, 10),
                                                                        Money.of(1000, "EUR"),
                                                                        null));
        // WHEN
        List<AccountStatementLineWithBalance> statementLines = accountStatements.getStatementLines();

        // THEN
        List<AccountStatementLineWithBalance> expectedStatementLines = List.of(new AccountStatementLineWithBalance(LocalDate.of(2012, Month.JANUARY, 10),
                                                                                                                   Money.of(1000, "EUR"),
                                                                                                                   null,
                                                                                                                   Money.of(1000, "EUR")));
        assertThat(statementLines).isEqualTo(expectedStatementLines);
    }
}
