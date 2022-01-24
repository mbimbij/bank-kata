package org.example.banking.domain.event;

import java.lang.reflect.ParameterizedType;

public abstract class AEventHandler<T extends ADomainEvent> {
    private Class<T> handledEventType;

    public AEventHandler() {
        this.handledEventType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract void handle(T domainEvent);

    public boolean isHandled(ADomainEvent domainEvent){
        return handledEventType.isAssignableFrom(domainEvent.getClass());
    }
}
