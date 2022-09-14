package com.mt_ag.bayer.cmc.core.service.notification;

import com.mt_ag.bayer.cmc.persistence.entity.Measurement;

public interface NotificationService {

    String CROP_MONITORING_COCKPIT = "Crop_Monitoring_Cockpit";

    String BUSINESS_SEGMENTS = "Crop Small Molecules";

    String MARGOT_EMAIL = "margot.boergartz@bayer.com";
    String LAVANYA_EMAIL = "lavanya.devasenathipathi.ext@bayer.com";
   // String MARGOT_EMAIL = "lavanya.devasenathipathi.ext@bayer.com";

    String MARGOT_NAME = "Margot,Boergartz";
    String LAVANYA_NAME = "Lavanya,Devasenathipathi";
   // String MARGOT_NAME = "Lavanya,Devasenathipathi";

    String LEAD_PROBLEM = "ST - Unauthorized Residues";
  //  String LEAD_PROBLEM= "ST - Environmental / Water/ Soil / Air";

    String NONE = "None";

    String LEAD_PRODUCT = ".SEE ACTIVE INGREDIENT";

    NotificationResult notifyCairNew(Measurement measurement);

}
