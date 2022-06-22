package com.mt_ag.bayer.cmc.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mt_ag.bayer.cmc.persistence.entity.Country;

public interface CountryRepository extends CrudRepository<Country, Long> {
	Country findByNameIgnoreCaseEquals(String name);
	List<Country> findByOtherNamesIgnoreCaseEquals(String anotherName);
	List<Country> findAll();
}
