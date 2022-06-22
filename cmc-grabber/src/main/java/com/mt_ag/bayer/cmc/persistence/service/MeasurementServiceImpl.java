package com.mt_ag.bayer.cmc.persistence.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt_ag.bayer.cmc.persistence.entity.Measurement;
import com.mt_ag.bayer.cmc.persistence.entity.Substance;

@Service
public class MeasurementServiceImpl implements MeasurementService {

	private MeasurementRepository repository;

	@Autowired
	public MeasurementServiceImpl(MeasurementRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void save(Measurement measurement) {
		this.repository.save(measurement);
	}

	@Override
	public List<Measurement> findAll() {
		List<Measurement> mmds = new ArrayList<>();
		this.repository.findAll().forEach(mmds::add);
		return mmds;
	}

	@Override
	public Measurement find(Long id) {
		return this.repository.findById(id).get();
	}

	@Override
	public void delete(Measurement measurement) {
		this.repository.delete(measurement);
	}

	@Override
	public void deleteAll() {
		this.repository.deleteAll();
	}

	@Override
	public List<Measurement> findBySamplingDateGreaterThan(Date samplingDate) {
		return this.repository.findBySamplingDateGreaterThan(convertToLocalDateTimeViaInstant(samplingDate));
	}

	@Override
	public List<Measurement> findBySubstance(Substance substance) {
		return this.repository.findBySubstance(substance);
	}

	@Override
	public Measurement findTopByOrderBySamplingDateDesc() {
		return this.repository.findTopByOrderBySamplingDateDesc();
	}

	@Override
	public List<Measurement> findBySamplingDateGreaterThanEqual(Date samplingDate) {
		return this.repository.findBySamplingDateGreaterThanEqual(samplingDate);
	}
	
	public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}

}
