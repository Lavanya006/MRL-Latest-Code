package com.mt_ag.bayer.cmc.persistence.service;

import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.Unit;

public interface UnitService {

	void save(Unit unit);

	List<Unit> findAll();

	Unit find(Long id);

	void delete(Unit unit);

	Unit findByName(String name);

}