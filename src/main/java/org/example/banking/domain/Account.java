package org.example.banking.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Account {
    final UUID id;
    final Customer owner;
    final Collection<ADomainEvent> uncommittedChanges = new ArrayList<>();
    MonetaryAmount balance = Money.of(0, "EUR");

    public Account(@NonNull UUID id, @NonNull Customer owner) {
        this.id = id;
        this.owner = owner;
        uncommittedChanges.add(new AccountCreatedEvent(id, owner.getId()));
    }

    public Collection<ADomainEvent> getUncommittedChanges() {
        return uncommittedChanges;
    }

    public void deposit(MonetaryAmount monetaryAmount) {
        balance = balance.add(monetaryAmount);
        uncommittedChanges.add(new MoneyDeposittedEvent(id, monetaryAmount));
    }

    public void withdraw(MonetaryAmount monetaryAmount) {
        balance = balance.subtract(monetaryAmount);
        uncommittedChanges.add(new MoneyWithdrawnEvent(id, monetaryAmount));
    }

    public MonetaryAmount getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }

    public void transferOut(UUID destinationAccountId, MonetaryAmount monetaryAmount) {
        balance = balance.subtract(monetaryAmount);
        uncommittedChanges.add(new MoneyTransferredOutEvent(id, destinationAccountId, monetaryAmount));
    }

    public void transferIn(UUID sourceAccountId, MonetaryAmount monetaryAmount) {
        balance = balance.add(monetaryAmount);
        uncommittedChanges.add(new MoneyTransferredInEvent(id, sourceAccountId, monetaryAmount));
    }
}
