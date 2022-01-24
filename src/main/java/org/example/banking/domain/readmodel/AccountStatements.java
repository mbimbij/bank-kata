package org.example.banking.domain.readmodel;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountStatements {
    MonetaryAmount currentBalance = Money.of(0, "EUR");
    private final List<AccountStatementLineWithBalance> statementLinesWithBalance = new ArrayList<>();

    public AccountStatements() {
    }

    public static AccountStatements fromStatementLinesWithBalance(List<AccountStatementLineWithBalance> statementLineWithBalances) {
        AccountStatements accountStatements = new AccountStatements();
        if (statementLineWithBalances.isEmpty()) {
            return accountStatements;
        }

        accountStatements.statementLinesWithBalance.addAll(statementLineWithBalances);
        accountStatements.currentBalance = statementLineWithBalances.get(statementLineWithBalances.size() - 1).getBalance();
        return accountStatements;
    }

    public static AccountStatements fromStatementLinesWithoutBalance(Collection<AccountStatementLineWithoutBalance> statementLineWithoutBalances) {
        AccountStatements accountStatements = new AccountStatements();
        statementLineWithoutBalances.forEach(accountStatements::handle);
        return accountStatements;
    }

    List<AccountStatementLineWithBalance> getStatementLines() {
        return statementLinesWithBalance;
    }

    public void handle(AccountStatementLineWithoutBalance statementLine) {
        AccountStatementLineWithBalance newStatementLineWithBalance = statementLine.withBalance(currentBalance);
        statementLinesWithBalance.add(newStatementLineWithBalance);
        currentBalance = newStatementLineWithBalance.getBalance();
    }
}
