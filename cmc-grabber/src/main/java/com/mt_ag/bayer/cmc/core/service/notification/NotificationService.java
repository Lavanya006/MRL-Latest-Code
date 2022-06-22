package com.mt_ag.bayer.cmc.core.service.notification;

import com.mt_ag.bayer.cmc.persistence.entity.Measurement;

public interface NotificationService {

    String CROP_MONITORING_COCKPIT = "Crop_Monitoring_Cockpit";

    String BUSINESS_SEGMENTS = "Crop Small Molecules";

    String MARGOT_EMAIL = "margot.boergartz@bayer.com";

    String MARGOT_NAME = "Margot,Boergartz";

    String LEAD_PROBLEM = "ST - Unauthorized Residues";

    String NONE = "None";

    String LEAD_PRODUCT = ".SEE ACTIVE INGREDIENT";

    NotificationResult notifyCairNew(Measurement measurement);

}
