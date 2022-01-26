package org.example.banking.adapter.in;

import org.example.banking.application.ApplicationFacade;
import org.example.banking.domain.writemodel.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping(path = "/customers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public class CustomerRestController {
    @Autowired
    private ApplicationFacade applicationFacade;

    @PostMapping
    public Mono<String> createCustomer(@RequestBody String customerName) {
        UUID customerId = UUID.randomUUID();
        applicationFacade.createCustomer(customerId, customerName);
        return Mono.just(customerId.toString());
    }

    @GetMapping("/{id}")
    public Mono<CustomerDto> getCustomerData (@PathVariable(name = "id") String customerId) {
        Customer customer = applicationFacade.getCustomerById(UUID.fromString(customerId));
        return Mono.just(CustomerDto.fromCustomer(customer));
    }
}
