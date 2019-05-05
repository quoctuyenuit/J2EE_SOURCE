package com.j2ee.j2eeproject.service;

import java.util.List;

import com.j2ee.j2eeproject.entity.User;

public interface UserServiceInterface {
	Iterable<User> findAll();

    List<User> search(String term);

    User findOne(Integer id);

    void save(User contact);

    void delete(Integer id);
}
