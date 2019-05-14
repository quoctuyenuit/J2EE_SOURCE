package com.j2ee.j2eeproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.j2ee.j2eeproject.entity.UserType;

@Repository
public interface UserTypeRepository extends CrudRepository<UserType, Integer>{
	List<UserType> findByNameContaining(String term);
}
