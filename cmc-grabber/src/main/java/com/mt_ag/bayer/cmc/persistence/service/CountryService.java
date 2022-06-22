package com.mt_ag.bayer.cmc.persistence.service;

import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.Country;

public interface CountryService {

	void save(Country country);

	List<Country> findAll();

	Country find(Long id);

	void delete(Country country);

	Country findByName(String name);
	
	List<Country> findByOtherNames(String anotherName);

}
