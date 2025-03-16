package com.teja.dayana.webapp.data.repositories;

import com.teja.dayana.webapp.data.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    CustomerEntity findByEmail(String email);
}
