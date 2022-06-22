package com.mt_ag.bayer.cmc.core.init;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

import com.mt_ag.bayer.cmc.core.tools.TimeTools;
import com.mt_ag.bayer.cmc.persistence.entity.User;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserMasterData extends MasterData<User> {

    @Override
    public void init() {
        LOGGER.info("Initializing Users...");
        try {
            List<User> users = loadJson();
            save(users);
        } catch (Exception e) {
            LOGGER.error("Could not initialize Users", e);
        }
    }

    @Override
    public boolean shouldInit() {
        return this.repository.count() == 0;
    }

    @Override
    protected void save(List<User> users) {
        Temporal start = LocalDateTime.now();
        users.parallelStream().forEach(user -> {
            this.repository.save(user);
        });
        Temporal end = LocalDateTime.now();
        LOGGER.info(users.size() + " Users saved in "
                + TimeTools.calculateDuration(start, end));
    }

    @Override
    protected List<User> loadJson() {
        try {
            Resource resource = resourceLoader.getResource("classpath:master-data/users.json");
            InputStream jsonInputStream = resource.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonInputStream, new TypeReference<List<User>>() {
            });
        } catch (Exception e) {
            LOGGER.error("Could not load Users", e);
            return null;
        }
    }

}
