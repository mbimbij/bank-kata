package org.example.banking.application;

import org.example.banking.domain.writemodel.AccountRepository;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public abstract class ACommandHandler <T extends ACommand> {
  private Class<T> handledEventType;
  protected final AccountRepository accountRepository;

  public ACommandHandler(AccountRepository aggregateRepository) {
    this.handledEventType = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
    this.accountRepository = aggregateRepository;
  }

  public abstract void handle(T domainEvent);

  public boolean isHandled(ACommand domainEvent) {
    return Objects.equals(handledEventType, domainEvent.getClass());
  }
}
