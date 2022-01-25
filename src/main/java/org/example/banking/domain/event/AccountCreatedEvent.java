package org.example.banking.domain.event;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.example.banking.domain.writemodel.Customer;

import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountCreatedEvent extends ADomainEvent {
    Customer customer;

    public AccountCreatedEvent(UUID accountId, Customer customer, ZonedDateTime timestamp) {
        super(accountId, timestamp);
        this.customer = customer;
    }
}
