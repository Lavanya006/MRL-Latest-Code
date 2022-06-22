package com.mt_ag.bayer.cmc.persistence.service;

import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.Family;

public interface FamilyService {

	void save(Family family);

	List<Family> findAll();

	Family find(Long id);

	void delete(Family family);

	Family findByName(String name);

}