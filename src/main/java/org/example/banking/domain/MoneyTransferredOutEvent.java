package org.example.banking.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.money.MonetaryAmount;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MoneyTransferredOutEvent extends ADomainEvent {
    UUID destinationAccountId;
    final MonetaryAmount transferAmount;

    public MoneyTransferredOutEvent(UUID accountId, UUID destinationAccountId, MonetaryAmount transferAmount) {
        super(accountId);
        this.destinationAccountId = destinationAccountId;
        this.transferAmount = transferAmount;
    }
}
