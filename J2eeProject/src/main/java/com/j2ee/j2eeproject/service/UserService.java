package com.j2ee.j2eeproject.service;

import java.util.List;
import java.util.Optional;

import com.j2ee.j2eeproject.entity.User;

public interface UserService {
	Iterable<User> findAll();

    List<User> search(String term);

    Optional<User> findOne(String id);

    void save(User user);

    void delete(String id);
}
