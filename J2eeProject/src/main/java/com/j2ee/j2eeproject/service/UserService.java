package com.j2ee.j2eeproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.j2ee.j2eeproject.entity.User;

@Service
public class UserService implements UserServiceInterface {

	@Override
	public Iterable<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> search(String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(User contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
