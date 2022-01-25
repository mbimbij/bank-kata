package org.example.banking.application;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.javamoney.moneta.Money;

import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
public class DepositMoneyCommand extends ACommand {
  private final Money depositAmount;

  public DepositMoneyCommand(UUID accountId, Money depositAmount, ZonedDateTime timestamp) {
    super(accountId, timestamp);
    this.depositAmount = depositAmount;
  }
}
