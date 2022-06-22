package com.mt_ag.bayer.cmc.core.init;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

import com.mt_ag.bayer.cmc.core.tools.TimeTools;
import com.mt_ag.bayer.cmc.persistence.entity.Crop;
import com.mt_ag.bayer.cmc.persistence.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CropMasterData extends MasterData<Crop> {

    @Autowired
    private FamilyRepository familyRepository;

    @Override
    public void init() {
        LOGGER.info("Initializing Crops");
        try {
            save(loadJson());
        } catch (Exception e) {
            LOGGER.error("Could not initialize Crops", e);
        }
    }

    @Override
    public boolean shouldInit() {
        return this.repository.count() == 0;
    }

    @Override
    protected void save(List<Crop> crops) {
        Temporal start = LocalDateTime.now();
        crops.parallelStream().forEach(crop -> {
            crop.setFamily(familyRepository.save(crop.getFamily()));
            this.repository.save(crop);
        });
        Temporal end = LocalDateTime.now();
        LOGGER.info(crops.size() + " Crops saved in "
                + TimeTools.calculateDuration(start, end));
    }

    @Override
    protected List<Crop> loadJson() {
        try {
            //Full CairNewAddon
            Resource resource = resourceLoader.getResource("classpath:master-data/crops.json");
            InputStream jsonInputStream = resource.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonInputStream, new TypeReference<List<Crop>>() {
            });
        } catch (Exception e) {
            LOGGER.error("Could not load Crops", e);
            return null;
        }
    }

}