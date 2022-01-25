package org.example.banking.application;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
public class CreateAccountCommand extends ACommand {
  private final UUID customerId;

  public CreateAccountCommand(UUID accountId, UUID customerId, ZonedDateTime timestamp) {
    super(accountId, timestamp);
    this.customerId = customerId;
  }
}
