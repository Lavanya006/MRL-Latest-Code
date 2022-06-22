package com.mt_ag.bayer.cmc.core.init;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

import com.mt_ag.bayer.cmc.core.tools.TimeTools;
import com.mt_ag.bayer.cmc.persistence.entity.DataSource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataSourceMasterData extends MasterData<DataSource> {

    @Override
    public void init() {
        LOGGER.info("Initializing Data Sources");
        try {
            save(loadJson());
        } catch (Exception e) {
            LOGGER.error("Could not initialize Data Sources", e);
        }
    }

    @Override
    public boolean shouldInit() {
        return this.repository.count() == 0;
    }

    @Override
    protected void save(List<DataSource> dataSources) {
        Temporal start = LocalDateTime.now();
        dataSources.parallelStream().forEach(dataSource -> {
            this.repository.save(dataSource);
        });
        Temporal end = LocalDateTime.now();
        LOGGER.info(dataSources.size() + " Data Sources saved in "
                + TimeTools.calculateDuration(start, end));
    }

    @Override
    protected List<DataSource> loadJson() {
        try {
            Resource resource = resourceLoader.getResource("classpath:master-data/data-sources.json");
            InputStream jsonInputStream = resource.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonInputStream, new TypeReference<List<DataSource>>() {
            });
        } catch (Exception e) {
            LOGGER.error("Could not load Data Sources", e);
            return null;
        }
    }

}
