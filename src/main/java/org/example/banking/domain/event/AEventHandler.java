package org.example.banking.domain.event;

import org.example.banking.domain.readmodel.AccountStatementsReadRepo;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public abstract class AEventHandler<T extends ADomainEvent> {
    private Class<T> handledEventType;
    protected final AccountStatementsReadRepo readRepo;

    public AEventHandler(AccountStatementsReadRepo readRepo) {
        this.handledEventType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.readRepo = readRepo;
    }

    public abstract void handle(T domainEvent);

    public boolean isHandled(ADomainEvent domainEvent) {
        return Objects.equals(handledEventType, domainEvent.getClass());
    }
}
