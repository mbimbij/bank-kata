package org.example.banking.domain.writemodel;

import javax.money.MonetaryAmount;

public class TransferService {
    public void transfer(Account source, Account destination, MonetaryAmount transferAmount) {
        source.transferOut(destination.getId(), transferAmount);
        destination.transferIn(source.getId(), transferAmount);
    }
}
