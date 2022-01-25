package org.example.banking.adapter.out;

import org.example.banking.domain.writemodel.Customer;
import org.example.banking.domain.writemodel.CustomerDoesNotExistException;
import org.example.banking.domain.writemodel.CustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryCustomerRepository implements CustomerRepository {
  private Map<UUID, Customer> customerMap = new HashMap<>();

  @Override
  public Customer getById(UUID customerId) {
    return Optional.ofNullable(customerMap.get(customerId))
                   .orElseThrow(() -> new CustomerDoesNotExistException(customerId.toString()));
  }

  @Override
  public void save(Customer customer) {
    customerMap.put(customer.getId(), customer);
  }
}
