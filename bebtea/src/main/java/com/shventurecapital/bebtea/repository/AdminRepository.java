package com.shventurecapital.bebtea.repository;

import com.shventurecapital.bebtea.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {

	//User findByEmail(String email);
}
