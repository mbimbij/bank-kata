package org.example.banking.domain.event;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public abstract class ADomainEvent {
    private final UUID accountId;
    private final ZonedDateTime timestamp;
}
