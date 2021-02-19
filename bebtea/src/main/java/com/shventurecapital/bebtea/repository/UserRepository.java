package com.shventurecapital.bebtea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shventurecapital.bebtea.models.User;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	//User findByEmail(String email);
}
