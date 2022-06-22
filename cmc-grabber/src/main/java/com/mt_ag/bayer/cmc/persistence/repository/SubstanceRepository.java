package com.mt_ag.bayer.cmc.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mt_ag.bayer.cmc.persistence.entity.Substance;

public interface SubstanceRepository extends CrudRepository<Substance, Long> {
	Substance findByNameIgnoreCaseEquals(String name);
	List<Substance> findByOtherNamesIgnoreCaseEquals(String anotherName);
}
