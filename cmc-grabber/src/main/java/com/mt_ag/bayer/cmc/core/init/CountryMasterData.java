package com.mt_ag.bayer.cmc.core.init;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

import com.mt_ag.bayer.cmc.core.tools.TimeTools;
import com.mt_ag.bayer.cmc.persistence.entity.Country;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CountryMasterData extends MasterData<Country> {

    @Override
    public void init() {
        LOGGER.info("Initializing Countries");
        try {
            save(loadJson());
        } catch (Exception e) {
            LOGGER.error("Could not initialize countries", e);
        }
    }

    @Override
    public boolean shouldInit() {
        return this.repository.count() == 0;
    }

    @Override
    protected void save(List<Country> countries) {
        Temporal start = LocalDateTime.now();
        countries.parallelStream().forEach(country -> {
            this.repository.save(country);
        });
        Temporal end = LocalDateTime.now();
        LOGGER.info(countries.size() + " Countries saved in "
                + TimeTools.calculateDuration(start, end));
    }

    @Override
    protected List<Country> loadJson() {
        try {
            Resource resource = resourceLoader.getResource("classpath:master-data/countries.json");
            InputStream jsonInputStream = resource.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new String(jsonInputStream.readAllBytes(), "UTF8"), new TypeReference<List<Country>>() {
            });
        } catch (Exception e) {
            LOGGER.error("Could not load Countries", e);
            return null;
        }
    }
}