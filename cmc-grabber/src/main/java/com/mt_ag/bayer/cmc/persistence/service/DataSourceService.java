package com.mt_ag.bayer.cmc.persistence.service;

import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.DataSource;

public interface DataSourceService {
	void save(DataSource dataSource);

	List<DataSource> findAll();

	DataSource find(Long id);

	void delete(DataSource dataSource);

	DataSource findByName(String name);
	
	boolean isUp(DataSource dataSource, boolean disableThrowExceptions);
}
