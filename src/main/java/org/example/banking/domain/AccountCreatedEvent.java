package org.example.banking.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
public class AccountCreatedEvent extends ADomainEvent {
    private final UUID customerId;

    public AccountCreatedEvent(UUID accountId, UUID customerId) {
        super(accountId);
        this.customerId = customerId;
    }
}
