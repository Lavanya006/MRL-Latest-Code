package com.mt_ag.bayer.cmc.controller;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.Country;
import com.mt_ag.bayer.cmc.persistence.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

	private CountryRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

	@Autowired
	public CountryController(CountryRepository repository) {
		super();
		this.repository = repository;
	}

	@CrossOrigin
	@GetMapping("/countries")
	public List<Country> findAll() {
		List<Country> countries = new ArrayList<>();
		this.repository.findAll().forEach(countries::add);
		return countries;
	}

	@CrossOrigin
	@RequestMapping("/countries/{id}")
	public Country findById(@PathVariable Long id) {
		return this.repository.findById(id).get();
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/countries")
	public void saveCountry(@RequestBody Country country) {
		try {
			this.repository.save(country);
		} catch (Exception e) {
			LOGGER.info("Saving failed", e);
		}

	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/countries")
	public void updateCountrye(@RequestBody Country country) {
		try {
			this.repository.save(country);
		} catch (Exception e) {
			LOGGER.info("Updating failed", e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/countries")
	public void deleteCountry(@RequestBody Country country) {
		this.repository.delete(country);
	}

}