package com.mt_ag.bayer.cmc.core.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class MasterData<T> {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(MasterData.class);
	
	@Autowired
	protected ResourceLoader resourceLoader;

	@Autowired
	protected CrudRepository<T, Long> repository;

	public abstract void init();

	public abstract boolean shouldInit();

	protected abstract void save(List<T> data);

	protected abstract List<T> loadJson();

}
