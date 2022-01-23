package org.example.banking.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.money.MonetaryAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EqualsAndHashCode
public class Account {
    UUID id;
    Customer owner;
    Collection<ADomainEvent> uncommittedChanges = new ArrayList<>();

    public Account(UUID id, Customer owner) {
        this.id = id;
        this.owner = owner;
        uncommittedChanges.add(new AccountCreatedEvent(id, owner.getId()));
    }

    public Collection<ADomainEvent> getUncommittedChanges() {
        return uncommittedChanges;
    }

    public void deposit(MonetaryAmount monetaryAmount) {
        uncommittedChanges.add(new MoneyDeposittedEvent(id, monetaryAmount));
    }
}
