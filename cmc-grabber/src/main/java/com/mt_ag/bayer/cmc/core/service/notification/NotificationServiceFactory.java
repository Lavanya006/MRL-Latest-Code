package com.mt_ag.bayer.cmc.core.service.notification;

import com.mt_ag.bayer.cmc.constants.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class NotificationServiceFactory {

    @Autowired
    private DevNotificationService devNotificationService;
    @Autowired
    private QaNotificationService qaNotificationService;
    @Autowired
    private ProdNotificationService prodNotificationService;

    @Value("${bayer.cairnew.environment}")
    private String executionEnvironment;

    public NotificationService getNotificationService() {
        switch (executionEnvironment) {
            case SystemConstants.DEV:
                return devNotificationService;
            case SystemConstants.PROD:
                return prodNotificationService;
            case SystemConstants.QA:
                return qaNotificationService;
            default:
                return null;
        }
    }

    @PostConstruct
    private void checkEnvironment() {
        if (executionEnvironment == null) {
            System.out.println("bayer.cairnew.environment property is missing..." +
                    "\nShould be one of: prod, qa, dev" +
                    "\nPlease check your property file and try again.");
            System.exit(1);
        } else {
            System.out.println("CURRENT EXECUTION ENVIRONMENT: " + executionEnvironment);
        }
    }
}
