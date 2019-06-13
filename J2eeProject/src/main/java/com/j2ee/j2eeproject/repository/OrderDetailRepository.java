package com.j2ee.j2eeproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.j2ee.j2eeproject.entity.pojo.OrderDetail;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, OrderDetail.PrimaryKey>{

}
