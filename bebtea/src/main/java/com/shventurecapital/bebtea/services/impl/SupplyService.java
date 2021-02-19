package com.shventurecapital.bebtea.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shventurecapital.bebtea.models.Supply;
import com.shventurecapital.bebtea.repository.SupplyRepository;
import com.shventurecapital.bebtea.services.ISupplyService;

@Service
public class SupplyService implements ISupplyService {
    @Autowired
	private SupplyRepository supplyRepository;
	
	@Override
	public Supply save(Supply entity) {
		return supplyRepository.save(entity);
	}

	@Override
	public Supply update(Supply entity) {
		return supplyRepository.save(entity);
	}

	@Override
	public void delete(Supply entity) {
		supplyRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		supplyRepository.deleteById(id);
	}

	@Override
	public Supply find(Long id) {
		return supplyRepository.findById(id).orElse(null);
	}

	@Override
	public List<Supply> findAll() {
		return supplyRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Supply> supply) {
		supplyRepository.deleteInBatch(supply);
	}
}
