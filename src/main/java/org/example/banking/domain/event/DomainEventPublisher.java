package org.example.banking.domain.event;

import java.util.ArrayList;
import java.util.List;

public class DomainEventPublisher {
    List<AEventHandler> eventHandlers = new ArrayList<>();

    public void publish(List<ADomainEvent> domainEvents) {
        domainEvents.forEach(domainEvent -> {
            eventHandlers.stream()
                         .filter(aEventHandler -> aEventHandler.isHandled(domainEvent))
                         .forEach(aEventHandler -> aEventHandler.handle(domainEvent));
        });
    }

    public <T extends ADomainEvent> void register(AEventHandler<T> eventHandler) {
        eventHandlers.add(eventHandler);
    }
}
