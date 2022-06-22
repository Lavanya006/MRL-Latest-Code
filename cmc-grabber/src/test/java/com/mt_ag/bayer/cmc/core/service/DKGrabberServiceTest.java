package com.mt_ag.bayer.cmc.core.service;

import com.mt_ag.bayer.cmc.core.service.notification.HazardLevel;
import com.mt_ag.bayer.cmc.persistence.entity.DataSource;
import com.mt_ag.bayer.cmc.persistence.entity.Execution;
import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;
import com.mt_ag.bayer.cmc.persistence.entity.Measurement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class DKGrabberServiceTest {

    @Test
    void givenJobThenGrabData() {
        DKGrabberService dkGrabberService = new DKGrabberService();

        LocalDateTime executionDate = LocalDateTime.of(2020, 07, 01, 0, 0);
        DataSource dataSource = new DataSource(DataSource.DK_DATA_SOURCE, "National Food Institute DTU",
                "https://www.food.dtu.dk/english/publications/chemical-exposure/pesticides-in-the-diet",
                30);
        Execution execution = new Execution(executionDate, dataSource.getName());
        GrabbingJob grabbingJob = new GrabbingJob(execution, dataSource, 1593774181407L);

        try {
            dkGrabberService.grabData(grabbingJob);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void givenSpreadsheetInputStreamThenReturnMeasurement() {
        DKGrabberService dkGrabberService = new DKGrabberService();
        List<Measurement> measurements = null;
        String spreadsheetLink = "https://www.food.dtu.dk/english/-/media/Institutter/Foedevareinstituttet/Publikationer/Pub-2020/Pest2019-4-kvartal-undersoegte-proever.ashx?la=da&hash=9F6993DB8B59D93BE589649C63C49AD47A57F78E";

        LocalDateTime executionDate = LocalDateTime.of(2020, 07, 01, 0, 0);
        DataSource dataSource = new DataSource(DataSource.DK_DATA_SOURCE, "National Food Institute DTU",
                "https://www.food.dtu.dk/english/publications/chemical-exposure/pesticides-in-the-diet",
                30);
        Execution execution = new Execution(executionDate, dataSource.getName());
        GrabbingJob grabbingJob = new GrabbingJob(execution, dataSource, 1593774181407L);

        try (InputStream spreadsheetInputStream = new URL(spreadsheetLink).openStream()) {
            measurements = dkGrabberService
                    .grabMeasurementsFromSpreadsheet(grabbingJob, spreadsheetInputStream, spreadsheetLink);
        } catch (MalformedURLException e) {
            fail(e);
        } catch (IOException e) {
            fail(e);
        }

        Assertions.assertNotNull(measurements);
        Assertions.assertNotEquals(0, measurements.size());

        Measurement expectedMeasurement = measurements.stream().filter(m -> {
            if (m.getResidueLevel() == 0.018
                    && m.getMrl() == 0.6
                    && m.getMrlsign() == HazardLevel.GREEN) {
                return true;
            } else {
                return false;
            }
        }).findFirst().orElse(null);

        Assertions.assertNotNull(expectedMeasurement);
    }

    @Test
    void givenDataSourceUrlReturnLink() {
        String dataSourceUrl = "https://www.food.dtu.dk/english/publications/chemical-exposure/pesticides-in-the-diet";
        String expectedLink = "https://www.food.dtu.dk/english/-/media/Institutter/Foedevareinstituttet/Publikationer/Pub-2020/Pest2019-4-kvartal-undersoegte-proever.ashx?la=da&hash=9F6993DB8B59D93BE589649C63C49AD47A57F78E";
        GrabbingJob grabbingJob = new GrabbingJob();
        grabbingJob.setDataSource(new DataSource(null, null, dataSourceUrl, null));

        DKGrabberService dkGrabberService = new DKGrabberService();
        List<String> allSpreadsheetLinks = dkGrabberService.grabAllSpreadsheetLinks(grabbingJob);
        Assertions.assertNotNull(allSpreadsheetLinks);
        Assertions.assertTrue(allSpreadsheetLinks.contains(expectedLink));
    }

}