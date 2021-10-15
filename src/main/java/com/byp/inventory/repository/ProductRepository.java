package com.byp.inventory.repository;

import com.byp.inventory.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByIdIn(List<Long> ids);
}
