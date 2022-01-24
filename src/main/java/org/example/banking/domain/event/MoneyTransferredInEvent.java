package org.example.banking.domain.event;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MoneyTransferredInEvent extends ADomainEvent {
    UUID sourceAccountId;
    final MonetaryAmount transferAmount;

    public MoneyTransferredInEvent(UUID accountId, UUID sourceAccountId, MonetaryAmount transferAmount, ZonedDateTime timestamp) {
        super(accountId, timestamp);
        this.sourceAccountId = sourceAccountId;
        this.transferAmount = transferAmount;
    }
}
