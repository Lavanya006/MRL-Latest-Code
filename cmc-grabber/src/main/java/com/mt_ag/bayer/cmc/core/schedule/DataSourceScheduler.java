package com.mt_ag.bayer.cmc.core.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;

public abstract class DataSourceScheduler {

	public abstract void init();
	
	protected abstract void scheduleNextExecution(boolean shouldExecuteNow);
	
	public abstract void starManualExecution();
	
	protected abstract LocalDateTime computeNextExecution();

	protected abstract void grabData();

	protected abstract void tryAgainInOneHour();

	protected abstract GrabbingJob createJob(LocalDateTime execution);

	public final Long generateUniqueHash() {
		return System.currentTimeMillis();
	}

	public final LocalDateTime setUpdateTime(LocalDateTime date) {
		LocalTime time = LocalTime.of(23, 59, 59);
		return date.with(time);
	}
}