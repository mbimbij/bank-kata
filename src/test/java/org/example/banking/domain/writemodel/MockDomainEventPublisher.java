package org.example.banking.domain.writemodel;

import lombok.Getter;
import org.example.banking.domain.event.ADomainEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class MockDomainEventPublisher extends DomainEventPublisher {
  List<ADomainEvent> publishedEvents = new ArrayList<>();

  @Override
  public void publish(Collection<ADomainEvent> domainEvents) {
    super.publish(domainEvents);
    publishedEvents.addAll(domainEvents);
  }
}
