package com.mt_ag.bayer.cmc.persistence.service;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt_ag.bayer.cmc.persistence.entity.Unit;

@Service
public class UnitServiceImpl implements UnitService {

	private UnitRepository repository;

	@Autowired
	public UnitServiceImpl(UnitRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void save(Unit unit) {
		this.repository.save(unit);

	}

	@Override
	public List<Unit> findAll() {
		List<Unit> units = new ArrayList<>();
		this.repository.findAll().forEach(units::add);
		return units;
	}

	@Override
	public Unit find(Long id) {
		return this.repository.findById(id).get();
	}

	@Override
	public void delete(Unit unit) {
		this.repository.delete(unit);
	}

	@Override
	public Unit findByName(String name) {
		List<Unit> units = this.repository.findByNameIgnoreCaseEquals(name);
		return (units.size() > 0) ? units.get(0) : null;
	}
}
