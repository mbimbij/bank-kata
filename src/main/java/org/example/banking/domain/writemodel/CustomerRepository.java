package org.example.banking.domain.writemodel;

import java.util.UUID;

public interface CustomerRepository {
  Customer getById(UUID customerId);
  void save(Customer customer);
}
