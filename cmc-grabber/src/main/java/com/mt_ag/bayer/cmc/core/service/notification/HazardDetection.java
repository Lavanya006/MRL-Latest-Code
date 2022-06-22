package com.mt_ag.bayer.cmc.core.service.notification;

import com.mt_ag.bayer.cmc.persistence.entity.Measurement;

public class HazardDetection {
    public static int whatLevel(Measurement measurement) {
        if (measurement.getResidueLevel() == 0) {
            return HazardLevel.GREEN;
        } else if (measurement.getResidueLevel() > measurement.getMrl()) {
            return HazardLevel.RED;
        } else if (measurement.getResidueLevel() < (measurement.getMrl() * 0.7)) {
            return HazardLevel.YELLOW;
        } else {
            return HazardLevel.ORANGE;
        }
    }

}
