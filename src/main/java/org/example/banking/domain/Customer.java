package org.example.banking.domain;

import lombok.Value;

import java.util.UUID;

@Value
public class Customer {
    UUID id;
    String name;
}
