package com.mt_ag.bayer.cmc.core.service.notification;

import com.mt_ag.bayer.cmc.persistence.entity.Measurement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HazardDetectionTest {

    @Test
    void testLevelOrange() {
        Measurement measurement = new Measurement();
        measurement.setResidueLevel(0.7);
        measurement.setMrl(1.0);
        int level = HazardDetection.whatLevel(measurement);
        Assertions.assertEquals(HazardLevel.ORANGE, level);
    }
}