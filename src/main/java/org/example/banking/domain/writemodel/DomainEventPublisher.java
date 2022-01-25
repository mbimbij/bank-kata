package org.example.banking.domain.writemodel;

import org.example.banking.domain.event.ADomainEvent;
import org.example.banking.domain.event.AEventHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DomainEventPublisher {
  List<AEventHandler> eventHandlers = new ArrayList<>();

  public void publish(Collection<ADomainEvent> domainEvents) {
    domainEvents.forEach(domainEvent -> {
      eventHandlers.stream()
                   .filter(eventHandler -> eventHandler.isHandled(domainEvent))
                   .forEach(eventHandler -> eventHandler.handle(domainEvent));
    });
  }

  public <T extends ADomainEvent> void register(AEventHandler<T> eventHandler) {
    eventHandlers.add(eventHandler);
  }
}
