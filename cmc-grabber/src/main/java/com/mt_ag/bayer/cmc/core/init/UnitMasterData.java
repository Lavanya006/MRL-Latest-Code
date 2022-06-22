package com.mt_ag.bayer.cmc.core.init;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

import com.mt_ag.bayer.cmc.core.tools.TimeTools;
import com.mt_ag.bayer.cmc.persistence.entity.Unit;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UnitMasterData extends MasterData<Unit> {

	@Override
	public void init() {
		LOGGER.info("Initializing Units...");
		try {
			save(loadJson());
		} catch (Exception e) {
			LOGGER.error("Could not initialize units", e);
		}
	}

	@Override
	public boolean shouldInit() {
		return this.repository.count() == 0;
	}

	@Override
	protected void save(List<Unit> units) {
		Temporal start = LocalDateTime.now();
		units.parallelStream().forEach(unit -> {
			this.repository.save(unit);
		});
		Temporal end = LocalDateTime.now();
		LOGGER.info(units.size() + " Units saved in "
				+ TimeTools.calculateDuration(start, end));
	}

	@Override
	protected List<Unit> loadJson() {
		try {
			Resource resource = resourceLoader.getResource("classpath:master-data/units.json");
			InputStream jsonInputStream = resource.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(jsonInputStream, new TypeReference<List<Unit>>() {
			});
		} catch (Exception e) {
			LOGGER.error("Could not load Units", e);
			return null;
		}

	}

}
