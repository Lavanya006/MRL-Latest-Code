package com.mt_ag.bayer.cmc.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mt_ag.bayer.cmc.persistence.entity.Crop;
import com.mt_ag.bayer.cmc.persistence.entity.Family;

public interface CropRepository extends CrudRepository<Crop, Long> {
	Crop findByNameAndFamily(String name, Family group);
	Crop findByNameIgnoreCaseEquals(String name);
	List<Crop> findByOtherNamesIgnoreCaseEquals(String anotherName);
}
