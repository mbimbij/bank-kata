package org.example.banking.adapter.in;

import lombok.Value;
import org.example.banking.domain.writemodel.Customer;

@Value
public class CustomerDto {
    String id;
    String name;

    public static CustomerDto fromCustomer(Customer customer) {
        return new CustomerDto(customer.getId().toString(), customer.getName());
    }
}
