package com.mt_ag.bayer.cmc.controller;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.Family;
import com.mt_ag.bayer.cmc.persistence.repository.FamilyRepository;
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
public class FamilyController {

	private FamilyRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(FamilyController.class);

	@Autowired
	public FamilyController(FamilyRepository repository) {
		super();
		this.repository = repository;
	}

	@CrossOrigin
	@GetMapping("/cropgroups")
	public List<Family> findAll() {
		List<Family> cropgroups = new ArrayList<>();
		this.repository.findAll().forEach(cropgroups::add);
		return cropgroups;
	}

	@CrossOrigin
	@RequestMapping("/cropgroups/{id}")
	public Family findById(@PathVariable Long id) {
		return this.repository.findById(id).get();
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/cropgroups")
	public void saveCropgroup(@RequestBody Family cropgroup) {
		try {
			this.repository.save(cropgroup);
		} catch (Exception e) {
			LOGGER.info("Saving failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/cropgroups")
	public void updateCropgroup(@RequestBody Family cropgroup) {
		try {
			this.repository.save(cropgroup);
		} catch (Exception e) {
			LOGGER.info("Updating failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/cropgroups")
	public void deleteCountry(@RequestBody Family cropgroup) {
		this.repository.delete(cropgroup);
	}
}