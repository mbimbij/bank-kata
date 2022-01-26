package org.example.banking.application;

import org.example.banking.adapter.out.InMemoryAccountStatementsReadRepo;
import org.example.banking.domain.writemodel.AccountDoesNotExistException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ApplicationFacadeTest {
    @Test
    void applicationShouldThrownException_whenNoAccountCreated() {
        // GIVEN
        ApplicationFacade applicationFacade = new ApplicationFacade(new InMemoryAccountStatementsReadRepo());
        UUID accountId = UUID.randomUUID();

        // WHEN - THEN
        assertThatThrownBy(() -> applicationFacade.getAccountStatements(accountId)).isInstanceOf(AccountDoesNotExistException.class);
    }
}
