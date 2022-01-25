package org.example.banking.adapter.out;

import lombok.Getter;
import org.example.banking.domain.event.ADomainEvent;
import org.example.banking.domain.writemodel.EventStore;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class InMemoryEventStore implements EventStore {
  private List<ADomainEvent> events = new ArrayList<>();

  @Override
  public void save(Collection<ADomainEvent> domainEvents) {
    events.addAll(domainEvents);
  }

  @Override
  public Collection<ADomainEvent> getById(UUID accountId) {
    return events.stream()
                 .filter(aDomainEvent -> Objects.equals(aDomainEvent.getAccountId(), accountId))
                 .collect(Collectors.toList());
  }
}
