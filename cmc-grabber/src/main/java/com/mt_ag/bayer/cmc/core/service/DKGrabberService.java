package com.mt_ag.bayer.cmc.core.service;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mt_ag.bayer.cmc.constants.SystemConstants;
import com.mt_ag.bayer.cmc.core.mapping.DKMapper;
import com.mt_ag.bayer.cmc.core.mapping.WebClientFactory;
import com.mt_ag.bayer.cmc.core.service.notification.HazardLevel;
import com.mt_ag.bayer.cmc.core.service.notification.NotificationResult;
import com.mt_ag.bayer.cmc.core.service.notification.NotificationServiceFactory;
import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;
import com.mt_ag.bayer.cmc.persistence.entity.Measurement;
import com.mt_ag.bayer.cmc.persistence.service.MeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DKGrabberService implements GrabberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DKGrabberService.class);

    private final String MAINCONTENT_XPATH = "//div[contains(@class, 'contenttext')]";
    private final String A_TAG_XPATH = "//a";
    private final String HREF_ATTRIBUTE = "href";
    private final String DOMAIN = "https://www.food.dtu.dk";

    @Value("${bayer.cairnew.notification}")
    private String cairNewNotificationStatus;

    @Autowired
    private DKMapper dkMappingService;
    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private NotificationServiceFactory notificationServiceFactory;
    @Autowired
    private WebClientFactory webClientFactory;

    @Override
    public void grabData(GrabbingJob job) {
        for (String link : grabAllSpreadsheetLinks(job)) {
            WebClient webClient = webClientFactory.create();
            try {

                Page ods = null;
                try {
                    ods = webClient.getPage(link);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<Measurement> measurements = grabMeasurementsFromSpreadsheet(job, ods.getWebResponse().getContentAsStream(), link);
                if (measurements.isEmpty()) {
                    LOGGER.info("Skipping non XLS File: " + link);
                    continue;
                } else {
                    LOGGER.info("Saving " +
                            measurements.size() + " from the XLS: " + link);
                }
                measurements.parallelStream().filter(m -> (m.getCrop() != null)
                        && (m.getMrl() != null)
                        && (m.getResidueLevel() != null)
                        && (m.getSamplingCountry() != null)
                        && (m.getOriginCountry() != null)
                        && (m.getSubstance() != null)
                        && (m.getUnit() != null))
                        .forEach(measurement -> {
                            if (this.measurementService != null) {
                                measurementService.save(measurement);
                            } else {
                                LOGGER.debug("Saving Measurement...\n" + measurement);
                            }

                            if (cairNewNotificationStatus != null) {
                                if (this.cairNewNotificationStatus.equals(SystemConstants.IS_ON)) {
                                    if (measurement.getMrlsign() == HazardLevel.RED) {
                                        NotificationResult result = notificationServiceFactory.getNotificationService().notifyCairNew(measurement);
                                        if (result != NotificationResult.SUCCESS) {
                                            LOGGER.error("Could not notify Measurement with id " + measurement.getId());
                                        }
                                    }
                                }
                            } else {
                                if (measurement.getMrlsign() == HazardLevel.RED) {
                                    LOGGER.debug("Notifying CairNew...\nHazard Level: " + HazardLevel.RED);
                                }
                            }
                        });
            } catch (IOException e) {
                LOGGER.warn("Could not open the URL: " + link);
            } finally {
                webClient.close();
            }
        }
    }

    List<String> grabAllSpreadsheetLinks(GrabbingJob job) {
        if (webClientFactory == null) {
            webClientFactory = new WebClientFactory();
        }
        WebClient webClient = webClientFactory.create();
        webClient.getOptions().setJavaScriptEnabled(false);
        List<String> allSpreadsheetLinks = new ArrayList<>();

        try {
            HtmlPage page = webClient.getPage(job.getDataSource().getUrl());
            List<DomElement> elements = page.getByXPath(MAINCONTENT_XPATH);
            List<HtmlAnchor> dataFilesLinks = elements.get(0).getByXPath(A_TAG_XPATH);

            for (HtmlAnchor a : dataFilesLinks) {
                String link = DOMAIN + a.getAttribute(HREF_ATTRIBUTE);
                if (link.startsWith(DKMapper.SPREADSHEET_LINK_BEGIN)) {
                    allSpreadsheetLinks.add(link);
                    LOGGER.info(link);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Could not fetch Spreadsheet links", e);
        } finally {
            webClient.close();
        }
        return allSpreadsheetLinks;
    }

    List<Measurement> grabMeasurementsFromSpreadsheet(GrabbingJob job, InputStream spreadsheetInputStream, String dataSourceInfo) {
        if (dkMappingService == null) {
            dkMappingService = new DKMapper();
        }
        return dkMappingService
                .map(job, null, spreadsheetInputStream, dataSourceInfo)
                .stream()
                .collect(Collectors.toList());
    }
}