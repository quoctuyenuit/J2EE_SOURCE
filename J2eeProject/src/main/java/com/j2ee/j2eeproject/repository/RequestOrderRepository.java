package com.j2ee.j2eeproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.j2ee.j2eeproject.entity.pojo.RequestOrder;

@Repository
public interface RequestOrderRepository extends CrudRepository<RequestOrder, Integer>, RequestOrderRepositoryCustom{

}
