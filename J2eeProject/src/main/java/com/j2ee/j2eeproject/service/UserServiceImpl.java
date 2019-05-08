package com.j2ee.j2eeproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j2ee.j2eeproject.entity.User;
import com.j2ee.j2eeproject.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Iterable<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public List<User> search(String term) {
		// TODO Auto-generated method stub
		return userRepository.findByEmailContaining(term);
	}

	@Override
	public Optional<User> findOne(String id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}
}
