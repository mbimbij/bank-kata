package org.example.banking.adapter.out;

import lombok.Getter;
import org.example.banking.domain.event.ADomainEvent;
import org.example.banking.domain.writemodel.EventStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class InMemoryEventStore implements EventStore {
  private List<ADomainEvent> events = new ArrayList<>();

  @Override
  public void save(Collection<ADomainEvent> domainEvents) {
    events.addAll(domainEvents);
  }
}
