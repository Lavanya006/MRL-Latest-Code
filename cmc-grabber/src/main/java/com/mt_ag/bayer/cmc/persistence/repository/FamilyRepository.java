package com.mt_ag.bayer.cmc.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.mt_ag.bayer.cmc.persistence.entity.Family;

public interface FamilyRepository extends CrudRepository<Family, Long> {
	Family findByName_Equals(String name);
}
