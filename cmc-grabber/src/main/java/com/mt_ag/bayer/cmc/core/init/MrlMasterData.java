package com.mt_ag.bayer.cmc.core.init;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

import com.mt_ag.bayer.cmc.core.tools.TimeTools;
import com.mt_ag.bayer.cmc.persistence.entity.MRL;
import com.mt_ag.bayer.cmc.persistence.repository.MrlProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MrlMasterData extends MasterData<MRL> {

    @Autowired
    private MrlProductRepository productRepository;

    @Override
    public void init() {
        LOGGER.info("Initializing MRLs...");
        try {
            save(loadJson());
        } catch (Exception e) {
            LOGGER.error("Could not initialize Mrls", e);
        }
    }

    @Override
    public boolean shouldInit() {
        return this.repository.count() == 0;
    }

    @Override
    protected void save(List<MRL> mrls) {
        Temporal start = LocalDateTime.now();
        mrls.parallelStream().forEach(mrl -> {
            this.productRepository.saveAll(mrl.getMrlProducts());
            this.repository.save(mrl);
        });
        Temporal end = LocalDateTime.now();
        LOGGER.info(mrls.size() + " Mrls saved in "
                + TimeTools.calculateDuration(start, end));
    }

    @Override
    protected List<MRL> loadJson() {
        try {
            Resource resource = resourceLoader.getResource("classpath:master-data/mrl.json");
            InputStream jsonInputStream = resource.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonInputStream, new TypeReference<List<MRL>>() {
            });
        } catch (Exception e) {
            LOGGER.error("Could not load Mrls", e);
            return null;
        }
    }

}