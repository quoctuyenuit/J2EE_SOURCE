package com.j2ee.j2eeproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.j2ee.j2eeproject.entity.pojo.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>, ProductRepositoryCustom {
	List<Product> findByNameContaining(String name);
}
