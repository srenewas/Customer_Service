package com.customer.Service;

import com.customer.ExceptionHandler.CustomerNotFoundException;
import com.customer.Model.Customer;
import com.customer.Repository.CustomerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repository;

    private static final Logger logger = LogManager.getLogger(CustomerService.class);


    /*public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }*/

    public Customer createCustomer(Customer customer) {
        if (repository.existsById(customer.getId())) {
            logger.error("Customer already exists with ID: {}", customer.getId());
            throw new IllegalArgumentException("Customer already exists with ID: " + customer.getId());
        }
        return repository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            logger.error("Customer Not Found with id: {}",id);
            return new CustomerNotFoundException("Customer not found");
        });

    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        existing.setCity(customer.getCity());

        return repository.save(existing);
    }


    public void deleteCustomer(Long id) {
        if (!repository.existsById(id)){
            throw  new CustomerNotFoundException("Can't delete. Customer not found with ID: "+id);
        }else{
            repository.deleteById(id);
        }
    }
}