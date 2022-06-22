package com.mt_ag.bayer.cmc.controller;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.Substance;
import com.mt_ag.bayer.cmc.persistence.repository.SubstanceRepository;
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
public class SubstanceController {

	private SubstanceRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(SubstanceController.class);

	@Autowired
	public SubstanceController(SubstanceRepository repository) {
		super();
		this.repository = repository;
	}

	@CrossOrigin
	@GetMapping("/substances")
	public Iterable<Substance> findAll() {
		List<Substance> substances = new ArrayList<>();
		this.repository.findAll().forEach(substances::add);
		return substances;
	}

	@CrossOrigin
	@RequestMapping("/substances/{id}")
	public Substance findById(@PathVariable Long id) {
		return this.repository.findById(id).get();
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/substances")
	public void saveSubstance(@RequestBody Substance substance) {
		try {
			this.repository.save(substance);
		} catch (Exception e) {
			LOGGER.info("Saving failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/substances")
	public void updateSubstance(@RequestBody Substance substance) {
		try {
			this.repository.save(substance);
		} catch (Exception e) {
			LOGGER.info("Updating failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/substances")
	public void deleteCountry(@RequestBody Substance substance) {
		this.repository.delete(substance);
	}
}
