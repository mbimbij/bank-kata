package org.example.banking.domain.event;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountCreatedEvent extends ADomainEvent {
    UUID customerId;

    public AccountCreatedEvent(UUID accountId, UUID customerId, ZonedDateTime timestamp) {
        super(accountId, timestamp);
        this.customerId = customerId;
    }
}
