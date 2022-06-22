package com.mt_ag.bayer.cmc.controller;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.DataSource;
import com.mt_ag.bayer.cmc.persistence.repository.DataSourcesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataSourceController {

	private DataSourcesRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceController.class);

	@Autowired
	public DataSourceController(DataSourcesRepository repository) {
		super();
		this.repository = repository;
	}

	@CrossOrigin
	@GetMapping("/datasources")
	public List<DataSource> findAll() {
		List<DataSource> ds = new ArrayList<>();
		this.repository.findAll().forEach(ds::add);
		return ds;
	}

	@CrossOrigin
	@RequestMapping("/datasources/{id}")
	public DataSource findById(@PathVariable Long id) {
		return this.repository.findById(id).get();
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/datasources")
	public void saveDataSource(@RequestBody DataSource datasource) {
		try {
			this.repository.save(datasource);
		} catch (Exception e) {
			LOGGER.info("Saving failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/datasources")
	public void updateDataSource(@RequestBody DataSource datasource) {
		try {
			this.repository.save(datasource);
		} catch (Exception e) {
			LOGGER.info("Updating failed", e);
		}
	}

}
