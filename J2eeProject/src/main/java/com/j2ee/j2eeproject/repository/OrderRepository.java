package com.j2ee.j2eeproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.j2ee.j2eeproject.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{
	List<Order> findByUserIdContaining(String userId);
}
