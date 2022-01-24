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
public class MoneyDeposittedEvent extends ADomainEvent {
    MonetaryAmount deposit;

    public MoneyDeposittedEvent(UUID accountId, MonetaryAmount deposit, ZonedDateTime timestamp) {
        super(accountId, timestamp);
        this.deposit = deposit;
    }
}
