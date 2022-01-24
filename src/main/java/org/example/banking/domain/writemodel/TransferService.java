package org.example.banking.domain.writemodel;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public class TransferService {
    public void transfer(Account source, Account destination, MonetaryAmount transferAmount, ZonedDateTime moneyTransferTimestamp) {
        source.transferOut(destination.getId(), transferAmount, moneyTransferTimestamp);
        destination.transferIn(source.getId(), transferAmount, moneyTransferTimestamp);
    }
}
