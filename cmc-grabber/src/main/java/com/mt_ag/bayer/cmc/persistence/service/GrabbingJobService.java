package com.mt_ag.bayer.cmc.persistence.service;

import java.time.LocalDateTime;
import java.util.List;
import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;

public interface GrabbingJobService {

	void save(GrabbingJob grabbingJob);

	List<GrabbingJob> findAll();

	GrabbingJob find(Long id);

	void delete(GrabbingJob grabbingJob);

	GrabbingJob findByUniqueHash(Long uniqueHash);

	LocalDateTime getLastExecution(String dataSourceName);

	LocalDateTime getLastExecution(GrabbingJob grabbingJob);
}
