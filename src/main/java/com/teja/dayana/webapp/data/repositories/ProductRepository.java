package com.teja.dayana.webapp.data.repositories;

import com.teja.dayana.webapp.data.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

}
