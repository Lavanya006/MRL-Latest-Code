package com.mt_ag.bayer.cmc.controller;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.Unit;
import com.mt_ag.bayer.cmc.persistence.repository.UnitRepository;
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
public class UnitController {

	private UnitRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UnitController.class);

	@Autowired
	public UnitController(UnitRepository repository) {
		super();
		this.repository = repository;
	}

	@CrossOrigin
	@GetMapping("/units")
	public List<Unit> findAll() {
		List<Unit> units = new ArrayList<>();
		this.repository.findAll().forEach(units::add);
		return units;
	}

	@CrossOrigin
	@RequestMapping("/units/{id}")
	public Unit findById(@PathVariable Long id) {
		return this.repository.findById(id).get();
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/units")
	public void saveUnit(@RequestBody Unit unit) {
		try {
			this.repository.save(unit);
		} catch (Exception e) {
			LOGGER.info("Saving failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/units")
	public void updateUnit(@RequestBody Unit unit) {
		try {
			this.repository.save(unit);
		} catch (Exception e) {
			LOGGER.info("Updating failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/units")
	public void deleteCountry(@RequestBody Unit unit) {
		this.repository.delete(unit);
	}
}
