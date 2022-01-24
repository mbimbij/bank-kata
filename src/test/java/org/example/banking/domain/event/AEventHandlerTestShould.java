package org.example.banking.domain.event;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AEventHandlerTestShould {

    private final Event1Handler event1Handler = new Event1Handler();

    @Test
    void handleEventsOfSameType() {
        // GIVEN
        Event1 event = new Event1();

        // WHEN
        boolean handled = event1Handler.isHandled(event);

        // THEN
        assertThat(handled).isTrue();
    }

    @Test
    void notHandleEventsOfCompletelyDifferentType() {
        // GIVEN
        Event2 event = new Event2();

        // WHEN
        boolean handled = event1Handler.isHandled(event);

        // THEN
        assertThat(handled).isFalse();
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
