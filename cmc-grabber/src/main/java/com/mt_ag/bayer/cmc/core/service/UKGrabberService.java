package com.mt_ag.bayer.cmc.core.service;

import com.mt_ag.bayer.cmc.constants.SystemConstants;
import com.mt_ag.bayer.cmc.core.service.notification.HazardLevel;
import com.mt_ag.bayer.cmc.core.service.notification.NotificationResult;
import com.mt_ag.bayer.cmc.core.service.notification.NotificationServiceFactory;
import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;
import com.mt_ag.bayer.cmc.persistence.service.MeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mt_ag.bayer.cmc.core.mapping.UKMapper;

@Service
public class UKGrabberService implements GrabberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UKGrabberService.class);

    @Value("${bayer.cairnew.notification}")
    private String cairNewNotificationStatus;

    @Autowired
    private UKMapper ukMapper;
    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private NotificationServiceFactory notificationServiceFactory;

    @Autowired
    public UKGrabberService(UKMapper ukMapper) {
        super();
        this.ukMapper = ukMapper;
    }

    @Override
    public void grabData(GrabbingJob job) {
        ukMapper.map(job, null, null, null).parallelStream().forEach(measurement -> {
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
    }
}