package org.example.banking.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandBus {
    List<ACommandHandler> commandHandlers = new ArrayList<>();

    public void send(ACommand command) {
        List<ACommandHandler> commandHandlers = this.commandHandlers.stream()
                .filter(commandHandler -> commandHandler.isHandled(command))
                .collect(Collectors.toList());

        if(commandHandlers.isEmpty()){
            throw new NoHandlerDefinedForCommand(command);
        }

        commandHandlers.forEach(commandHandler -> commandHandler.handle(command));
    }

    public <T extends ACommand> void register(ACommandHandler<T> eventHandler) {
        commandHandlers.add(eventHandler);
    }
}
