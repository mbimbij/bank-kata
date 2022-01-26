package org.example.banking.domain.readmodel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
public class AccountStatements {
    MonetaryAmount currentBalance = Money.of(0, "EUR");
    private final List<AccountStatementLineWithBalance> statementLinesWithBalance = new ArrayList<>();

    public AccountStatements() {
    }

    public static AccountStatements fromStatementLinesWithBalance(MonetaryAmount balance, List<AccountStatementLineWithBalance> statementLineWithBalances) {
        AccountStatements accountStatements = new AccountStatements();
        accountStatements.currentBalance = balance;
        accountStatements.statementLinesWithBalance.addAll(statementLineWithBalances);
        return accountStatements;
    }

    List<AccountStatementLineWithBalance> getStatementLines() {
        return statementLinesWithBalance;
    }

    public void handle(AccountStatementLineWithoutBalance statementLine) {
        AccountStatementLineWithBalance newStatementLineWithBalance = statementLine.withBalance(currentBalance);
        statementLinesWithBalance.add(0, newStatementLineWithBalance);
        currentBalance = newStatementLineWithBalance.getBalance();
    }
}
