package com.j2ee.j2eeproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.j2ee.j2eeproject.entity.TakenOrder;

@Repository
public interface OrderRepository extends CrudRepository<TakenOrder, Integer>{
	List<TakenOrder> findByUserIdContaining(String userId);
}
