package org.example.banking.application;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
public class WithdrawMoneyCommand extends ACommand {
    private MonetaryAmount monetaryAmount;

    public WithdrawMoneyCommand(UUID accountId, ZonedDateTime timestamp, MonetaryAmount monetaryAmount) {
        super(accountId, timestamp);
        this.monetaryAmount = monetaryAmount;
    }
}
