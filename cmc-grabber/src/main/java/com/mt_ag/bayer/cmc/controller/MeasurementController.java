package com.mt_ag.bayer.cmc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.mt_ag.bayer.cmc.persistence.entity.Measurement;
import com.mt_ag.bayer.cmc.persistence.repository.MeasurementRepository;
import com.mt_ag.bayer.cmc.persistence.repository.SubstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasurementController {

	private MeasurementRepository measurementRepository;
	private SubstanceRepository substanceRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementController.class);

	@Autowired
	public MeasurementController(MeasurementRepository measurementRepository,
			SubstanceRepository substanceRepository) {
		super();
		this.measurementRepository = measurementRepository;
		this.substanceRepository = substanceRepository;
	}

	@CrossOrigin
	@GetMapping(path="/measurements" , produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Measurement> findAll() {
		LOGGER.info("Measurments printing...");
		List<Measurement> cropgroups = new ArrayList<>();
		this.measurementRepository.findAll().forEach(cropgroups::add);
		return cropgroups;
	}
	
	
	@CrossOrigin
	@GetMapping("/measurements/info")
	public String loadInfo() {
		
		LOGGER.info("Measurments info printing...");
		String response = "please wait...";
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		try {
			int meaCount = 0, datacount = 0;
			for(Measurement mea : this.measurementRepository.findAll()) {
				meaCount ++;
				String source = mea.getGrabbingJob().getDataSource().getName();
				if(data.get(source) == null) {
					datacount = 0;
				} else {
					datacount = data.get(source)  +1;
				}
				data.put(source, datacount);
			}
		    response =  "Measurements: " + meaCount + ", Entries:</br>";
			for(Entry<String, Integer> value : data.entrySet()) {
				response += " -" + value.getKey() + ": " + value.getValue() +  "</br>";
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return response;
		
	}
	
	

	@CrossOrigin
	@RequestMapping("/measurements/{id}")
	public Measurement findById(@PathVariable Long id) {
		return this.measurementRepository.findById(id).get();
	}

	@CrossOrigin
	@GetMapping("/measurements/substance/{substancename}")
	public List<Measurement> findBySubstance(@PathVariable String substancename) {
		return this.measurementRepository
				.findBySubstance(this.substanceRepository.findByNameIgnoreCaseEquals(substancename));
	}

	@CrossOrigin
	@GetMapping("/measurements/top")
	public Measurement findTopDate() {
		return this.measurementRepository.findTopByOrderBySamplingDateDesc();
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/measurements")
	public void saveMeasurement(@RequestBody Measurement measurement) {
		try {
			this.measurementRepository.save(measurement);
		} catch (Exception e) {
			LOGGER.info("Saving failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/measurements")
	public void updateMeasurement(@RequestBody Measurement measurement) {
		try {
			this.measurementRepository.save(measurement);
		} catch (Exception e) {
			LOGGER.info("Updating failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/measurements")
	public void deleteMeasurement(@RequestBody Measurement measurement) {
		this.measurementRepository.delete(measurement);
	}
}
