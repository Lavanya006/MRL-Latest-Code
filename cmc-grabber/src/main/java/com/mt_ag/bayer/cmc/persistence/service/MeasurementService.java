package com.mt_ag.bayer.cmc.persistence.service;

import java.util.Date;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.Measurement;
import com.mt_ag.bayer.cmc.persistence.entity.Substance;

public interface MeasurementService {
	void save(Measurement measurement);

	List<Measurement> findAll();

	Measurement find(Long id);

	void delete(Measurement measurement);

	void deleteAll();

	List<Measurement> findBySamplingDateGreaterThan(Date samplingDate);

	List<Measurement> findBySubstance(Substance substance);

	Measurement findTopByOrderBySamplingDateDesc();
	
	List<Measurement> findBySamplingDateGreaterThanEqual(Date samplingDate);
}
