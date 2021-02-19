package com.shventurecapital.bebtea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shventurecapital.bebtea.models.Supply;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

	//User findByEmail(String email);
}
