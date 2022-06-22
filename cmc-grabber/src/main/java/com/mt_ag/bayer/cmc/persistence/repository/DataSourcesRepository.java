package com.mt_ag.bayer.cmc.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.mt_ag.bayer.cmc.persistence.entity.DataSource;

public interface DataSourcesRepository extends CrudRepository<DataSource, Long> {
	DataSource findByName_IgnoreCaseContaining(String name);
}