package org.example.banking.adapter.in;

import org.example.banking.application.*;
import org.example.banking.domain.readmodel.AccountStatements;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping(path = "/accounts", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public class AccountRestController {
    @Autowired
    private ApplicationFacade applicationFacade;

    @PostMapping("/customer/{customerId}")
    public Mono<String> createAccount(@PathVariable("customerId") String customerIdString) {
        UUID accountId = UUID.randomUUID();
        UUID customerId = UUID.fromString(customerIdString);
        ZonedDateTime creationTimestamp = ZonedDateTime.now();
        applicationFacade.createAccount(new CreateAccountCommand(accountId, customerId, creationTimestamp));
        return Mono.just(accountId.toString());
    }

    @GetMapping("/{accountId}")
    public Mono<AccountStatementsDto> getAccountStatements(@PathVariable("accountId") String accountIdString) {
        UUID accountId = UUID.fromString(accountIdString);
        AccountStatements accountStatements = applicationFacade.getAccountStatements(accountId);
        AccountStatementsDto returnDto = AccountStatementsDto.fromDomainObject(accountStatements);
        return Mono.just(returnDto);
    }

    @PostMapping("/{accountId}/deposit/{amount}/{currency}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> depositMoney(@PathVariable("accountId") String accountIdString,
                                   @PathVariable("amount") double amount,
                                   @PathVariable("currency") String currency) {
        UUID accountId = UUID.fromString(accountIdString);
        ZonedDateTime depositTimestamp = ZonedDateTime.now();
        applicationFacade.deposit(new DepositMoneyCommand(accountId, Money.of(amount, currency), depositTimestamp));
        return Mono.empty();
    }

    @PostMapping("/{accountId}/withdraw/{amount}/{currency}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> withdrawMoney(@PathVariable("accountId") String accountIdString,
                                    @PathVariable("amount") double amount,
                                    @PathVariable("currency") String currency) {
        UUID accountId = UUID.fromString(accountIdString);
        ZonedDateTime timestamp = ZonedDateTime.now();
        applicationFacade.withdraw(new WithdrawMoneyCommand(accountId, Money.of(amount, currency), timestamp));
        return Mono.empty();
    }

    @PostMapping("/{accountId}/transfer/{destinationAccountId}/{amount}/{currency}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> transferMoney(@PathVariable("accountId") String accountIdString,
                                    @PathVariable("destinationAccountId") String destinationAccountIdString,
                                    @PathVariable("amount") double amount,
                                    @PathVariable("currency") String currency) {
        UUID accountId = UUID.fromString(accountIdString);
        UUID destinationAccountId = UUID.fromString(destinationAccountIdString);
        ZonedDateTime timestamp = ZonedDateTime.now();
        applicationFacade.tranfer(new TransferMoneyCommand(accountId, destinationAccountId, Money.of(amount, currency), timestamp));
        return Mono.empty();
    }
}
