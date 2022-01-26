package org.example.banking.application;

public class NoHandlerDefinedForCommand extends RuntimeException {
    public NoHandlerDefinedForCommand(ACommand command) {
        super(command.toString());
    }
}
