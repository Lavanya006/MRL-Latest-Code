package com.mt_ag.bayer.cmc.persistence.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.repository.GrabbingJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;

@Service
public class GrabbingJobServiceImpl implements GrabbingJobService {

	private GrabbingJobRepository repository;

	@Autowired
	public GrabbingJobServiceImpl(GrabbingJobRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void save(GrabbingJob grabbingJob) {
		this.repository.save(grabbingJob);
	}

	@Override
	public List<GrabbingJob> findAll() {
		List<GrabbingJob> jobs = new ArrayList<>();
		this.repository.findAll().forEach(jobs::add);
		return jobs;
	}

	@Override
	public GrabbingJob find(Long id) {
		return this.repository.findById(id).get();
	}

	@Override
	public void delete(GrabbingJob grabbingJob) {
		this.repository.delete(grabbingJob);
	}

	@Override
	public GrabbingJob findByUniqueHash(Long uniqueHash) {
		return this.repository.findByUniqueHash(uniqueHash);
	}

	@Override
	public LocalDateTime getLastExecution(GrabbingJob job) {
		GrabbingJob found = this.repository.findTop1ByExecution_DataSourceNameAndUniqueHashLessThan(
				job.getDataSource().getName(), job.getUniqueHash());
		if (found != null) {
			if (found.getExecution() != null) {
				return found.getExecution().getDateTime();
			}
		}
		return null;
	}

	@Override
	public LocalDateTime getLastExecution(String dataSourceName) {
		GrabbingJob found = this.repository.findTop1ByExecution_DataSourceNameOrderByUniqueHashDesc(dataSourceName);
		if (found != null) {
			if (found.getExecution() != null) {
				return found.getExecution().getDateTime();
			}
		}
		return null;
	}

}
