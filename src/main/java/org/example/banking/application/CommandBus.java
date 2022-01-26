package org.example.banking.application;

import org.example.banking.adapter.out.InMemoryCustomerRepository;
import org.example.banking.domain.writemodel.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class CommandBus {
    List<ACommandHandler> commandHandlers = new ArrayList<>();

    public void send(ACommand command) {
        commandHandlers.stream()
                .filter(commandHandler -> commandHandler.isHandled(command))
                .forEach(commandHandler -> commandHandler.handle(command));
    }

    public <T extends ACommand> void register(ACommandHandler<T> eventHandler) {
        commandHandlers.add(eventHandler);
    }
}
