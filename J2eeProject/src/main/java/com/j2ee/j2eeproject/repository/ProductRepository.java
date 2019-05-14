package com.j2ee.j2eeproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.j2ee.j2eeproject.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{
}
