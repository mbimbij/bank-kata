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
public class TransferMoneyCommand extends ACommand {
    private final UUID destinationAccountId;
    private final Money transferAmount;

    public TransferMoneyCommand(UUID sourceAccountId, UUID destinationAccountId, Money transferAmount, ZonedDateTime transferTimestamp) {
        super(sourceAccountId, transferTimestamp);
        this.destinationAccountId = destinationAccountId;
        this.transferAmount = transferAmount;
    }
}
