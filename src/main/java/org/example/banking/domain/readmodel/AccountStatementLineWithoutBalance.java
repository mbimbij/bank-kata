package org.example.banking.domain.readmodel;

import lombok.ToString;
import lombok.Value;

import javax.money.MonetaryAmount;
import java.time.LocalDate;

@Value
@ToString
public class AccountStatementLineWithoutBalance {
    LocalDate date;
    MonetaryAmount credit;
    MonetaryAmount debit;

    public boolean isCreditLine() {
        return credit != null;
    }

    public boolean isDebitLine() {
        return debit != null;
    }

    public AccountStatementLineWithBalance withBalance(MonetaryAmount lastAmount) {
        if (isCreditLine()) {
            return AccountStatementLineWithBalance.createCreditLineWithBalance(date, credit, lastAmount);
        } else if (isDebitLine()) {
            return AccountStatementLineWithBalance.createDebitLineWithBalance(date, debit, lastAmount);
        }
        throw new IllegalStateException("line is neither a credit or a debit line: " + this);
    }
}
