package com.koula.ecommerce.customer;

import com.koula.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }


    public void updateCustomer(CustomerRequest request) {
        var customer = repository.findById(request.id()).orElseThrow(() -> new CustomerNotFoundException(
                String.format("Cannot update customer:: Customer with id '%s' not found", request.id())
        ));
        mergerCustomer(customer, request);
        repository.save(customer);
    }

    private void mergerCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstName())) customer.setFirstName(request.firstName());
        if(StringUtils.isNotBlank(request.lastName())) customer.setLastName(request.lastName());
        if(StringUtils.isNotBlank(request.email())) customer.setEmail(request.email());
        if (request.address() != null) customer.setAddress(request.address());
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll().stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        //return mapper.fromCustomer(repository.findById(customerId).get());
        return repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot find customer with id '%s'", customerId)
                ));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}
