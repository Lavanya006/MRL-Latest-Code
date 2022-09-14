package com.mt_ag.bayer.cmc.core.service;

import com.mt_ag.bayer.cmc.constants.SystemConstants;
import com.mt_ag.bayer.cmc.core.service.notification.HazardLevel;
import com.mt_ag.bayer.cmc.core.service.notification.NotificationResult;
import com.mt_ag.bayer.cmc.core.service.notification.NotificationServiceFactory;
import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;
import com.mt_ag.bayer.cmc.persistence.entity.Substance;
import com.mt_ag.bayer.cmc.persistence.repository.SubstanceRepository;
import com.mt_ag.bayer.cmc.persistence.service.MeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import com.mt_ag.bayer.cmc.core.mapping.RASFF2Mapper;
import com.mt_ag.bayer.cmc.core.mapping.RASFFMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class RASFFGrabberService implements GrabberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RASFFGrabberService.class);

    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private SubstanceRepository substanceService;
    @Autowired
    private RASFFMapper rasffMappingService;
    @Autowired
    private NotificationServiceFactory notificationServiceFactory;

    @Value("${bayer.cairnew.notification}")
    private String cairNewNotificationStatus;

    @Override
    public void grabData(GrabbingJob job) {
        List<Substance> substances = new ArrayList<>();
        substanceService.findAll().iterator().forEachRemaining(substances::add);

        substances.parallelStream().forEach(substance -> {
            rasffMappingService.map(job, substance, null, null)
                    .parallelStream().forEach(measurement -> {

                measurementService.save(measurement);

                if (this.cairNewNotificationStatus.equals(SystemConstants.IS_ON)) {
                    if (measurement.getMrlsign() == HazardLevel.RED) {
                        NotificationResult result = notificationServiceFactory
                                .getNotificationService()
                                .notifyCairNew(measurement);
                        if (result != NotificationResult.SUCCESS) {
                            LOGGER.error("Could not notify Measurement with id " + measurement.getId());
                        }
                    }
                }
            });
        });
    }
}