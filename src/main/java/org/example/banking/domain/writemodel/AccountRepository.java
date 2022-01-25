package org.example.banking.domain.writemodel;

import lombok.RequiredArgsConstructor;
import org.example.banking.domain.event.ADomainEvent;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class AccountRepository {
  private final EventStore eventStore;
  private final DomainEventPublisher domainEventPublisher;

  public void save(Account newAccount) {
    Collection<ADomainEvent> uncommittedChanges = newAccount.getUncommittedChanges();
    eventStore.save(uncommittedChanges);
    domainEventPublisher.publish(uncommittedChanges);
  }

  public Account getById(UUID accountId) {
    return Optional.of(eventStore.getById(accountId))
                   .map(Account::new)
                   .orElseThrow(() -> new AccountDoesNotExistException(accountId));
  }
}
