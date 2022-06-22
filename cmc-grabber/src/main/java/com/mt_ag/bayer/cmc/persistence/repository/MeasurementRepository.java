package com.mt_ag.bayer.cmc.persistence.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mt_ag.bayer.cmc.persistence.entity.Measurement;
import com.mt_ag.bayer.cmc.persistence.entity.Substance;

public interface MeasurementRepository extends CrudRepository<Measurement, Long> {
	List<Measurement> findBySamplingDateGreaterThan(LocalDateTime samplingDate);

	List<Measurement> findBySubstance(Substance substance);

	Measurement findTopByOrderBySamplingDateDesc();
	
	List<Measurement> findBySamplingDateGreaterThanEqual(Date samplingDate);
}