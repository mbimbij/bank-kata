package org.example.banking.domain.event;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AEventHandlerTestShould {
    @Test
    void handleEventsOfSameType() {
        // GIVEN
        Event1Handler event1Handler = new Event1Handler();
        Event1 event1 = new Event1();

        // WHEN
        boolean handled = event1Handler.isHandled(event1);

        // THEN
        assertThat(handled).isTrue();
    }

    private static class Event1 extends ADomainEvent {
        public Event1() {
            super(UUID.randomUUID());
        }
    }

    private static class SubEvent1 extends Event1 {
    }

    private static class Event2 extends ADomainEvent {
        public Event2() {
            super(UUID.randomUUID());
        }
    }

    private static class Event1Handler extends AEventHandler<Event1>{
        @Override
        public void handle(Event1 domainEvent) {

        }
    }

    private static class SubEvent1Handler extends AEventHandler<SubEvent1>{
        @Override
        public void handle(SubEvent1 domainEvent) {

        }
    }
}
