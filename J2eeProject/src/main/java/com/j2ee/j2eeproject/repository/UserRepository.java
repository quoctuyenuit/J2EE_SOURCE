package com.j2ee.j2eeproject.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.j2ee.j2eeproject.entity.pojo.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
	List<User> findByEmailContaining(String term);
}
