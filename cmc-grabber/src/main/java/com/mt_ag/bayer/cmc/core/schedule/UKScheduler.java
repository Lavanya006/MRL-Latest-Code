package com.mt_ag.bayer.cmc.core.schedule;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.mt_ag.bayer.cmc.persistence.entity.DataSource;
import com.mt_ag.bayer.cmc.persistence.entity.Execution;
import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;
import com.mt_ag.bayer.cmc.persistence.service.DataSourceService;
import com.mt_ag.bayer.cmc.persistence.service.GrabbingJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.mt_ag.bayer.cmc.core.service.UKGrabberService;

@Component
public class UKScheduler extends DataSourceScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(UKScheduler.class);

	private GrabbingJobService jobService;
	private DataSourceService dataSourceService;
	private UKGrabberService grabberService;
	private ThreadPoolTaskScheduler scheduler;
	private TaskExecutor taskExecutor;
	private DataSource dataSource;

	@Autowired
	public UKScheduler(GrabbingJobService jobService, DataSourceService dataSourceService,
			UKGrabberService grabberService, ThreadPoolTaskScheduler scheduler, TaskExecutor taskExecutor) {
		super();
		this.jobService = jobService;
		this.dataSourceService = dataSourceService;
		this.grabberService = grabberService;
		this.scheduler = scheduler;
		this.taskExecutor = taskExecutor;
	}

	private Runnable grabbingTask = () -> {
		Thread.currentThread().setName("UK");
		grabData();
	};

	@Override
	public void init() {
		this.dataSource = this.dataSourceService.findByName(DataSource.UK_DATA_SOURCE);
		scheduleNextExecution(this.jobService.getLastExecution(DataSource.UK_DATA_SOURCE) == null);
	}

	@Override
	protected void scheduleNextExecution(boolean shouldExecuteNow) {
		if (this.dataSourceService.isUp(dataSource, true)) {
			if (shouldExecuteNow) {
				LOGGER.info("Grabbing UK for the first time now " + LocalDateTime.now());
				taskExecutor.execute(grabbingTask);
			}
			LocalDateTime nextExecution = computeNextExecution();
			scheduler.schedule(grabbingTask, nextExecution.toInstant(ZoneOffset.of("+02:00")));
			LOGGER.info("The next UK Grabbing will be on " + nextExecution);
		} else {
			this.tryAgainInOneHour();
		}

	}

	@Override
	protected LocalDateTime computeNextExecution() {
		LocalDateTime nextExecution = null;
		LocalDateTime lastExecution = this.jobService.getLastExecution(DataSource.UK_DATA_SOURCE);
		if (lastExecution != null) {
			nextExecution = this.setUpdateTime(lastExecution.plusDays(dataSource.getIntervalInDays()));
		} else {
			nextExecution = this.setUpdateTime(LocalDateTime.now().plusDays(dataSource.getIntervalInDays()));
		}
		return nextExecution;
	}

	@Override
	protected void grabData() {
		LocalDateTime execution = this.setUpdateTime(LocalDateTime.now());
		GrabbingJob job = createJob(execution);
		LOGGER.info("Grabbing from " + job.getDataSource().getName() + " at: " + job.getExecution());
		this.grabberService.grabData(job);
		this.scheduleNextExecution(false);
	}

	@Override
	protected void tryAgainInOneHour() {
		LOGGER.info("The UK DataSource was down. Will try again in One Hour");
		scheduler.schedule(grabbingTask, LocalDateTime.now().plusHours(1L).toInstant(ZoneOffset.of("+02:00")));
	}

	@Override
	protected GrabbingJob createJob(LocalDateTime execution) {
		GrabbingJob job = new GrabbingJob(new Execution(execution, DataSource.UK_DATA_SOURCE), dataSource,
				this.generateUniqueHash());
		this.jobService.save(job);
		return this.jobService.findByUniqueHash(job.getUniqueHash());
	}

	@Override
	public void starManualExecution() {
		if (this.dataSourceService.isUp(dataSource, true)) {

			LOGGER.info("Grabbing UK manually now " + LocalDateTime.now());
			taskExecutor.execute(grabbingTask);

			LocalDateTime nextExecution = computeNextExecution();
			scheduler.schedule(grabbingTask, nextExecution.toInstant(ZoneOffset.of("+02:00")));
			LOGGER.info("The next UK Grabbing will be on " + nextExecution);
		} else {
			LOGGER.info("Grabbing UK manually failed because the datasource is down.");
		}
	}

}
