package com.mt_ag.bayer.cmc.persistence.service;

import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.Crop;
import com.mt_ag.bayer.cmc.persistence.entity.Family;

public interface CropService {

	void save(Crop crop);

	List<Crop> findAll();

	Crop find(Long id);

	void delete(Crop crop);

	Crop findByNameAndCropGroup(String name, Family group);
	
	Crop findByName(String name);
	
	Crop findByOtherNames(String anotherName);

}
