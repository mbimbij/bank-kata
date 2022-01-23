package org.example.banking.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public abstract class ADomainEvent {
    private final UUID accountId;
}
