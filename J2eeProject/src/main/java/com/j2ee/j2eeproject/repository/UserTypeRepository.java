package com.j2ee.j2eeproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.j2ee.j2eeproject.entity.User_Type;

@Repository
public interface UserTypeRepository extends CrudRepository<User_Type, Integer>{
	List<User_Type> findByNameContaining(String term);
}
