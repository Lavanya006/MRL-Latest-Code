package com.mt_ag.bayer.cmc.persistence.service;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.repository.CropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt_ag.bayer.cmc.persistence.entity.Crop;
import com.mt_ag.bayer.cmc.persistence.entity.Family;

@Service
public class CropServiceImpl implements CropService {

	private CropRepository repository;

	@Autowired
	public CropServiceImpl(CropRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void save(Crop crop) {
		this.repository.save(crop);
	}

	@Override
	public List<Crop> findAll() {
		List<Crop> crops = new ArrayList<>();
		this.repository.findAll().forEach(crops::add);
		return crops;
	}

	@Override
	public Crop find(Long id) {
		return this.repository.findById(id).get();
	}

	@Override
	public void delete(Crop crop) {
		this.repository.delete(crop);
	}

	@Override
	public Crop findByNameAndCropGroup(String name, Family group) {
		return this.repository.findByNameAndFamily(name, group);
	}

	@Override
	public Crop findByName(String name) {
		return this.repository.findByNameIgnoreCaseEquals(name);
	}

	@Override
	public Crop findByOtherNames(String anotherName) {
		List<Crop> found = this.repository.findByOtherNamesIgnoreCaseEquals(anotherName);
		Crop crop = null;
		if (found != null) {
			if (found.size() > 0) {
				crop = found.get(0);
			}
		}
		return crop;
	}

}
