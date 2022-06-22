package com.mt_ag.bayer.cmc.persistence.service;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt_ag.bayer.cmc.persistence.entity.Family;

@Service
public class FamilyServiceImpl implements FamilyService {

	private FamilyRepository repository;

	@Autowired
	public FamilyServiceImpl(FamilyRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void save(Family family) {
		this.repository.save(family);
	}

	@Override
	public List<Family> findAll() {
		List<Family> crops = new ArrayList<>();
		this.repository.findAll().forEach(crops::add);
		return crops;
	}

	@Override
	public Family find(Long id) {
		return this.repository.findById(id).get();
	}

	@Override
	public void delete(Family family) {
		this.repository.delete(family);
	}

	@Override
	public Family findByName(String name) {
		return this.repository.findByName_Equals(name);
	}

}
