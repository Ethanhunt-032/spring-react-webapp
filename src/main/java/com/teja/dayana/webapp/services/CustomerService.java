package com.teja.dayana.webapp.services;

import com.teja.dayana.webapp.data.entities.CustomerEntity;
import com.teja.dayana.webapp.data.repositories.CustomerRepository;
import com.teja.dayana.webapp.web.errors.NotFoundException;
import com.teja.dayana.webapp.web.models.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    private CustomerEntity translateWebTooDb(Customer customer){
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getCustomerId());
        entity.setFirstName(customer.getFirstName());
        entity.setLastName(customer.getLastName());
        entity.setEmail(customer.getEmailAddress());
        entity.setPhone(customer.getPhoneNumber());
        entity.setAddress(customer.getAddress());
        return entity;
    }

    private Customer translateDbToWeb(CustomerEntity entity){
        Customer customer = new Customer();
        customer.setCustomerId(entity.getId());
        customer.setFirstName(entity.getFirstName());
        customer.setLastName(entity.getLastName());
        customer.setEmailAddress(entity.getEmail());
        customer.setPhoneNumber(entity.getPhone());
        customer.setAddress(entity.getAddress());
        return customer;
    }

    public List<Customer> getAllCustomers(String filterEmail) {
        List<Customer> customers = new ArrayList<>();
        if(StringUtils.hasLength(filterEmail)) {
            CustomerEntity entity = customerRepository.findByEmail(filterEmail);
            customers.add(this.translateDbToWeb(entity));
        }
        else {
            Iterable<CustomerEntity> entities = customerRepository.findAll();
            entities.forEach(entity -> {
                customers.add(this.translateDbToWeb(entity));});
        }
        return customers;
    }
    public Customer getCustomer(Long id) {
        Optional<CustomerEntity> OptionalEntity = customerRepository.findById(id);
        if(OptionalEntity.isEmpty()) {
            throw new NotFoundException("Customer Not Found with id " + id);
        }
        return this.translateDbToWeb(OptionalEntity.get());
    }
    public Customer createOrUpdate(Customer customer) {
        CustomerEntity entity = this.translateWebTooDb(customer);
        entity = this.customerRepository.save(entity);
        return this.translateDbToWeb(entity);
    }
    public void deleteCustomer(Long id) {

        this.customerRepository.deleteById(id);
    }
}
