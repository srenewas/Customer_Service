package com.customer.Controller;

import com.customer.Model.Customer;
import com.customer.Service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    private static final Logger logger = LogManager.getLogger(CustomerController.class);


    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        //customer.setId(null);
        Customer savedCustomer = service.createCustomer(customer);
        logger.info("Successfully Created with ==> {}", savedCustomer);
        return savedCustomer;
    }


    @GetMapping
    public List<Customer> getAll() {
        List<Customer> customers = service.getAllCustomers();
        logger.info("Successfully Fetching ==> {}",customers);
        return service.getAllCustomers();
    }

    @GetMapping("/count")
    public Map<String,Integer> getCount() {
        int count = service.getAllCustomers().size();
        logger.info("Total Count ==> {}",count);
        return Map.of("total_count",count);
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        Customer customer = service.getCustomerById(id);
        logger.info("Successfully fetched customer : {}", customer);
        return customer;
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
        return service.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        logger.info("Customer Deleted with id: {}",id);
        service.deleteCustomer(id);
        return "Customer Deleted with Id: "+id;
    }
}

