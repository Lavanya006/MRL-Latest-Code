package com.mt_ag.bayer.cmc.persistence.repository;


import com.mt_ag.bayer.cmc.persistence.entity.MRL;
import org.springframework.data.repository.CrudRepository;


public interface MrlRepository extends CrudRepository<MRL, Long> {
	
	MRL findBySubstanceNameIgnoreCaseEqualsAndMrlProducts_ProductNameIgnoreCaseEquals(String substancename, String prodname);
}
