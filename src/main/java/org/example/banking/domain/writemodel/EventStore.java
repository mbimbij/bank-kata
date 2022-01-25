package org.example.banking.domain.writemodel;

import org.example.banking.domain.event.ADomainEvent;

import java.util.Collection;
import java.util.UUID;

public interface EventStore {
  void save(Collection<ADomainEvent> domainEvents);

  Collection<ADomainEvent> getById(UUID accountId);
}
