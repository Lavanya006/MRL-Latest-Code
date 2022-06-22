package com.mt_ag.bayer.cmc.controller;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.Crop;
import com.mt_ag.bayer.cmc.persistence.repository.CropRepository;
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
public class CropController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CropController.class);
	private CropRepository repository;

	@Autowired
	public CropController(CropRepository repository) {
		super();
		this.repository = repository;
	}

	@CrossOrigin
	@GetMapping("/crops")
	public List<Crop> findAll() {
		List<Crop> crops = new ArrayList<>();
		this.repository.findAll().forEach(crops::add);
		return crops;
	}

	@CrossOrigin
	@RequestMapping("/crops/{id}")
	public Crop findById(@PathVariable Long id) {
		return this.repository.findById(id).get();
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/crops")
	public void save(@RequestBody Crop crop) {
		try {
			this.repository.save(crop);
		} catch (Exception e) {
			LOGGER.info("Saving failed", e);
		}
		this.repository.save(crop);
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/crops")
	public void updateCrop(@RequestBody Crop crop) {
		try {
			this.repository.save(crop);
		} catch (Exception e) {
			LOGGER.info("Updating failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/crops")
	public void deleteCountry(@RequestBody Crop crop) {
		this.repository.delete(crop);
	}
}
