package com.mt_ag.bayer.cmc.persistence.repository;

import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;

import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;

public interface GrabbingJobRepository extends CrudRepository<GrabbingJob, Long> {

	GrabbingJob findByUniqueHash(Long uniqueHash);

	GrabbingJob findTop1ByExecution_DataSourceNameAndUniqueHashLessThan(String dataSourcename, Long uniqueHash);

	GrabbingJob findTop1ByExecution_DataSourceNameOrderByUniqueHashDesc(String dataSourceName);

	GrabbingJob findTop1ByExecutionLessThan(LocalDateTime executionNow);

	GrabbingJob findTop1ByOrderByExecutionDesc();

}
