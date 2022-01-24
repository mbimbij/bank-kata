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
public class MoneyTransferredInEvent extends ADomainEvent {
    UUID destinationAccountId;
    final MonetaryAmount transferAmount;

    public MoneyTransferredInEvent(UUID accountId, UUID destinationAccountId, MonetaryAmount transferAmount) {
        super(destinationAccountId);
        this.destinationAccountId = accountId;
        this.transferAmount = transferAmount;
    }
}