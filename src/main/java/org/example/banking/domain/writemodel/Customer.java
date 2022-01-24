package org.example.banking.domain.writemodel;

import lombok.Value;

import java.util.UUID;

@Value
public class Customer {
    UUID id;
    String name;
}
