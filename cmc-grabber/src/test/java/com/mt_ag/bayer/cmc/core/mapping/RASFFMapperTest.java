package com.mt_ag.bayer.cmc.core.mapping;

import com.mt_ag.bayer.cmc.core.mapping.entity.RASFFMapping;
import com.mt_ag.bayer.cmc.persistence.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

class RASFFMapperTest {

    @Test
    void givenRASFFMappingAndSubstanceAndJobThenReturnMeasurement() {
        RASFFMapper rasffMapper = new RASFFMapper();
        Grabber grabber = rasffMapper.grabber;
        Assertions.assertNotNull(grabber);

        LocalDateTime executionDate = LocalDateTime.of(2020, 07, 01, 0, 0);
        DataSource dataSource = new DataSource(DataSource.RASFF_DATA_SOURCE, "European Commission – RASFF",
                "https://webgate.ec.europa.eu/rasff-window/portal/",
                15);
        Execution execution = new Execution(executionDate, dataSource.getName());
        GrabbingJob grabbingJob = new GrabbingJob(execution, dataSource, 1594886800111L);

        Substance substance = new Substance("procymidone", new HashSet<>());

        Set<RASFFMapping> rasffMappings = (Set<RASFFMapping>) grabber.grab(dataSource, substance);
        Assertions.assertNotNull(rasffMappings);
        Assertions.assertNotEquals(0, rasffMappings.size());

        Measurement measurement = rasffMapper.initMeasurement(rasffMappings.iterator().next(), substance, grabbingJob);
        Assertions.assertNotNull(measurement);
        Assertions.assertNotNull(measurement.getResidueLevel());
        Assertions.assertNotNull(measurement.getMrl());
    }

    @Test
    void givenDataSourceAndSubstanceThenReturnRASFFMappings() {
        Grabber grabber = new RASFFMapper().grabber;
        Assertions.assertNotNull(grabber);

        DataSource dataSource = new DataSource(DataSource.RASFF_DATA_SOURCE,
                "European Commission – RASFF",
                "https://webgate.ec.europa.eu/rasff-window/portal/",
                15);

        Substance substance = new Substance("procymidone", new HashSet<>());

        Set<RASFFMapping> rasffMappings = (Set<RASFFMapping>) grabber.grab(dataSource, substance);
        Assertions.assertNotNull(rasffMappings);
        Assertions.assertNotEquals(0, rasffMappings.size());
    }

}