package org.example.banking.domain.writemodel;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.example.banking.domain.event.ADomainEvent;
import org.example.banking.domain.event.AccountCreatedEvent;
import org.example.banking.domain.event.MoneyDeposittedEvent;
import org.example.banking.domain.event.MoneyTransferredInEvent;
import org.example.banking.domain.event.MoneyTransferredOutEvent;
import org.example.banking.domain.event.MoneyWithdrawnEvent;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
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

    public Account(@NonNull UUID id, @NonNull Customer owner, ZonedDateTime timestamp) {
        this.id = id;
        this.owner = owner;
        uncommittedChanges.add(new AccountCreatedEvent(id, owner.getId(), timestamp));
    }

    public Collection<ADomainEvent> getUncommittedChanges() {
        return uncommittedChanges;
    }

    public void deposit(MonetaryAmount monetaryAmount, ZonedDateTime timestamp) {
        balance = balance.add(monetaryAmount);
        uncommittedChanges.add(new MoneyDeposittedEvent(id, monetaryAmount, timestamp));
    }

    public void withdraw(MonetaryAmount monetaryAmount, ZonedDateTime timestamp) {
        balance = balance.subtract(monetaryAmount);
        uncommittedChanges.add(new MoneyWithdrawnEvent(id, monetaryAmount, timestamp));
    }

    public MonetaryAmount getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }

    public void transferOut(UUID destinationAccountId, MonetaryAmount monetaryAmount, ZonedDateTime timestamp) {
        balance = balance.subtract(monetaryAmount);
        uncommittedChanges.add(new MoneyTransferredOutEvent(id, destinationAccountId, monetaryAmount, timestamp));
    }

    public void transferIn(UUID sourceAccountId, MonetaryAmount monetaryAmount, ZonedDateTime timestamp) {
        balance = balance.add(monetaryAmount);
        uncommittedChanges.add(new MoneyTransferredInEvent(id, sourceAccountId, monetaryAmount, timestamp));
    }
}
