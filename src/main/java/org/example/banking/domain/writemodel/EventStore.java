package org.example.banking.domain.writemodel;

import org.example.banking.domain.event.ADomainEvent;

import java.util.Collection;

public interface EventStore {
  void save(Collection<ADomainEvent> domainEvents);
}
