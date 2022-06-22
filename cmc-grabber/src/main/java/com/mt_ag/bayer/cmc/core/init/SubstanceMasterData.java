package com.mt_ag.bayer.cmc.core.init;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

import com.mt_ag.bayer.cmc.core.tools.TimeTools;
import com.mt_ag.bayer.cmc.persistence.entity.Substance;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SubstanceMasterData extends MasterData<Substance> {

    @Override
    public void init() {
        LOGGER.info("Initializing Substances");
        try {
            List<Substance> substances = loadJson();
            save(substances);

        } catch (Exception e) {
            LOGGER.error("Could not initialize Substances", e);
        }
    }

    @Override
    public boolean shouldInit() {
        return this.repository.count() == 0;
    }

    @Override
    protected void save(List<Substance> substances) {
        Temporal start = LocalDateTime.now();
        substances.parallelStream().forEach(substance -> {
            this.repository.save(substance);
        });
        Temporal end = LocalDateTime.now();
        LOGGER.info(substances.size() + " Substances saved in "
                + TimeTools.calculateDuration(start, end));
    }

    @Override
    protected List<Substance> loadJson() {
        try {
            Resource resource = resourceLoader.getResource("classpath:master-data/substances.json");
            InputStream jsonInputStream = resource.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonInputStream, new TypeReference<List<Substance>>() {
            });
        } catch (Exception e) {
            LOGGER.error("Could not load Substances", e);
            return null;
        }
    }

}
