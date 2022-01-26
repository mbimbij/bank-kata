package org.example.banking.adapter.in;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.banking.domain.readmodel.AccountStatementLineWithBalance;
import org.example.banking.domain.readmodel.AccountStatements;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountStatementsDto {
    String currentBalance;
    List<AccountStatementLineWithBalanceDto> history;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class AccountStatementLineWithBalanceDto {
        String date;
        String credit;
        String debit;
        String balance;

        public static AccountStatementLineWithBalanceDto fromDomainObject(AccountStatementLineWithBalance domainObject) {
            return new AccountStatementLineWithBalanceDto(
                    domainObject.getDate().toString(),
                    Optional.ofNullable(domainObject.getCredit()).map(Objects::toString).orElse(""),
                    Optional.ofNullable(domainObject.getDebit()).map(Objects::toString).orElse(""),
                    domainObject.getBalance().toString()
            );
        }
    }

    public static AccountStatementsDto fromDomainObject(AccountStatements domainObject) {
        AccountStatementsDto dto = new AccountStatementsDto();
        dto.setCurrentBalance(domainObject.getCurrentBalance().toString());
        dto.history = domainObject.getStatementLinesWithBalance().stream()
                .map(AccountStatementLineWithBalanceDto::fromDomainObject)
                .collect(Collectors.toList());
        return dto;
    }
}
