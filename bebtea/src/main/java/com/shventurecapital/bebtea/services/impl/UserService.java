package com.shventurecapital.bebtea.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shventurecapital.bebtea.models.User;
import com.shventurecapital.bebtea.repository.UserRepository;
import com.shventurecapital.bebtea.services.IUserService;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User save(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public User update(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User find(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<User> users) {
		userRepository.deleteInBatch(users);
	}
	
}
