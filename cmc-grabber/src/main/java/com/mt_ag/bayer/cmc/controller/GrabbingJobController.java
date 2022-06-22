package com.mt_ag.bayer.cmc.controller;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;
import com.mt_ag.bayer.cmc.persistence.repository.GrabbingJobRepository;
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
public class GrabbingJobController {

	private GrabbingJobRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(GrabbingJobController.class);

	@Autowired
	public GrabbingJobController(GrabbingJobRepository repository) {
		super();
		this.repository = repository;
	}

	@CrossOrigin
	@GetMapping("/grabbingjobs")
	public List<GrabbingJob> findAll() {
		List<GrabbingJob> cropgroups = new ArrayList<>();
		this.repository.findAll().forEach(cropgroups::add);
		return cropgroups;
	}

	@CrossOrigin
	@RequestMapping("/grabbingjobs/{id}")
	public GrabbingJob findById(@PathVariable Long id) {
		return this.repository.findById(id).get();
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/grabbingjobs")
	public void saveGrabbingJob(@RequestBody GrabbingJob grabbingjob) {
		try {
			this.repository.save(grabbingjob);
		} catch (Exception e) {
			LOGGER.info("Saving failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/grabbingjobs")
	public void updateGrabbingJob(@RequestBody GrabbingJob grabbingjob) {
		try {
			this.repository.save(grabbingjob);
		} catch (Exception e) {
			LOGGER.info("Updating failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/grabbingjobs")
	public void deleteGrabbingJob(@RequestBody GrabbingJob grabbingjob) {
		this.repository.delete(grabbingjob);
	}

}
