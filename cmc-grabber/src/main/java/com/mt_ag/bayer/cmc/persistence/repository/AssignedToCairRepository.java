package com.mt_ag.bayer.cmc.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.mt_ag.bayer.cmc.persistence.entity.AssignedToCair;
import com.mt_ag.bayer.cmc.persistence.entity.Country;

public interface AssignedToCairRepository extends CrudRepository<AssignedToCair, Long> {

	AssignedToCair findByCountry(Country country);
}
