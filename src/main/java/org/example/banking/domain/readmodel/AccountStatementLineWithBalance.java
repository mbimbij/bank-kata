package org.example.banking.domain.readmodel;

import lombok.Value;

import javax.money.MonetaryAmount;
import java.time.LocalDate;

@Value
public class AccountStatementLineWithBalance {
    LocalDate date;
    MonetaryAmount credit;
    MonetaryAmount debit;
    MonetaryAmount balance;

    public static AccountStatementLineWithBalance createCreditLineWithBalance(LocalDate date,
                                                                              MonetaryAmount credit,
                                                                              MonetaryAmount lastBalance) {
        return new AccountStatementLineWithBalance(date, credit, null, lastBalance.add(credit));
    }

    public static AccountStatementLineWithBalance createDebitLineWithBalance(LocalDate date,
                                                                             MonetaryAmount debit,
                                                                             MonetaryAmount lastBalance) {
        return new AccountStatementLineWithBalance(date, null, debit, lastBalance.subtract(debit));
    }
}
