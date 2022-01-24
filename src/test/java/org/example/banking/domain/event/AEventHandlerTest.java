package org.example.banking.domain.event;

import org.example.banking.domain.readmodel.AccountStatementsReadRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class AEventHandlerTest {

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

    @Test
    void notHandleEventsOfSubType() {
        // GIVEN
        SubEvent1 event = new SubEvent1();

        // WHEN
        boolean handled = event1Handler.isHandled(event);

        // THEN
        assertThat(handled).isFalse();
    }

    @Test
    void notHandleEventsOfSuperType() {
        // GIVEN
        Event1 event = new Event1();
        SubEvent1Handler subEvent1Handler = new SubEvent1Handler();

        // WHEN
        boolean handled = subEvent1Handler.isHandled(event);

        // THEN
        assertThat(handled).isFalse();
    }

    private static class Event1 extends ADomainEvent {
        public Event1() {
            super(UUID.randomUUID(), ZonedDateTime.now());
        }
    }

    private static class SubEvent1 extends Event1 {
    }

    private static class Event2 extends ADomainEvent {
        public Event2() {
            super(UUID.randomUUID(), ZonedDateTime.now());
        }
    }

    private static class Event1Handler extends AEventHandler<Event1>{
        private Event1Handler() {
            super(mock(AccountStatementsReadRepo.class));
        }

        @Override
        public void handle(Event1 domainEvent) {

        }
    }

    private static class SubEvent1Handler extends AEventHandler<SubEvent1>{
        private SubEvent1Handler() {
            super(mock(AccountStatementsReadRepo.class));
        }

        @Override
        public void handle(SubEvent1 domainEvent) {

        }
    }
}
