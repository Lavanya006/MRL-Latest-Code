package com.mt_ag.bayer.cmc.core.mapping;

import com.mt_ag.bayer.cmc.core.service.notification.HazardLevel;
import com.mt_ag.bayer.cmc.persistence.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

class DKMapperTest {

    @Test
    void givenSpreadsheetlinkThenSamplingDate() {
        String spreadsheetLink = "https://www.food.dtu.dk/english/-/media/Institutter/Foedevareinstituttet/Publikationer/Pub-2020/Pest2019-4-kvartal-undersoegte-proever.ashx?la=da&hash=9F6993DB8B59D93BE589649C63C49AD47A57F78E";
        DKMapper dkMapper = new DKMapper();
        LocalDateTime defaultSamplingDate = dkMapper.computeDefaultSamplingDate(spreadsheetLink);
        Assertions.assertEquals(LocalDateTime.of(2019,11,15,0,0), defaultSamplingDate);
    }

    @Test
    void givenExcelSpreadsheetThenReturnFileAbsolutePath() {
        String resourceName = "Pest2019-4-kvartal-undersoegte-proever.xls";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();
        Assertions.assertTrue(absolutePath.endsWith("Pest2019-4-kvartal-undersoegte-proever.xls"));
    }

    @Test
    void givenFourthQuarter2019ExcelSpreadsheetThenReturnSet() {
        String resourceName = "Pest2019-4-kvartal-undersoegte-proever.xls";
        String spreadsheetLink = "https://www.food.dtu.dk/english/-/media/Institutter/Foedevareinstituttet/Publikationer/Pub-2020/Pest2019-4-kvartal-undersoegte-proever.ashx?la=da&hash=9F6993DB8B59D93BE589649C63C49AD47A57F78E";
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream xlsFileInputStream = null;
        try {
            xlsFileInputStream = new FileInputStream(classLoader.getResource(resourceName).getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Substance substance = new Substance("Prosulfocarb", new HashSet<>());
        DKMapper dkMapper = new DKMapper();
        LocalDateTime executionDate = LocalDateTime.of(2020, 07, 01, 0, 0);
        DataSource dataSource = new DataSource(DataSource.DK_DATA_SOURCE, "National Food Institute DTU",
                "https://www.food.dtu.dk/english/publications/chemical-exposure/pesticides-in-the-diet",
                30);
        Execution execution = new Execution(executionDate, dataSource.getName());
        GrabbingJob grabbingJob = new GrabbingJob(execution, dataSource, 1593774181407L);
        Set<Measurement> measurements = dkMapper.map(grabbingJob, substance, xlsFileInputStream, spreadsheetLink);
        Assertions.assertNotNull(measurements);
        Assertions.assertFalse(measurements.isEmpty());

        Measurement expectedMeasurement = measurements.stream().filter(m -> {
            if (m.getResidueLevel() == 0.1
                    && m.getMrl() == 0.05
                    && m.getMrlsign() == HazardLevel.RED) {
                return true;
            } else {
                return false;
            }
        }).findFirst().orElse(null);

        Assertions.assertNotNull(expectedMeasurement);
        Assertions.assertTrue(measurements.contains(expectedMeasurement));
    }

    @Test
    void givenSubstanceExactNameQueryThenFindSubstance() {

        String substanceNameQuery = "phenthoate";

        List<Substance> allSubstances = new ArrayList<>();
        Set<String> phenthoateOtherNames = new HashSet<>();
        phenthoateOtherNames.add("phenthoat");
        allSubstances.add(new Substance("phenthoate", phenthoateOtherNames));

        Set<String> piperonylbutoxideOtherNames = new HashSet<>();
        piperonylbutoxideOtherNames.add("piperonylbutoxid");
        piperonylbutoxideOtherNames.add("piperonyl");
        allSubstances.add(new Substance("piperonylbutoxide", piperonylbutoxideOtherNames));


        Substance result = allSubstances
                .stream()
                .filter(substance -> {
                    if (substance.getName().toUpperCase().equals(substanceNameQuery.toUpperCase())) {
                        return true;
                    } else {
                        if (substance.getOtherNames() == null) {
                            return false;
                        }
                        String otherName = substance.getOtherNames().stream()
                                .filter(o -> substanceNameQuery.toUpperCase().equals(o.toUpperCase()))
                                .findFirst().orElse(null);

                        if (otherName == null) {
                            return false;
                        }
                        return otherName.toUpperCase().equals(substanceNameQuery.toUpperCase());
                    }
                }).findFirst()
                .orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("phenthoate", result.getName());
    }

    @Test
    void givenSubstanceAnyCaseNameQueryThenFindSubstance() {

        String substanceNameQuery = "Phenthoate";

        List<Substance> allSubstances = new ArrayList<>();
        Set<String> phenthoateOtherNames = new HashSet<>();
        phenthoateOtherNames.add("phenthoat");
        allSubstances.add(new Substance("phenthoate", phenthoateOtherNames));

        Set<String> piperonylbutoxideOtherNames = new HashSet<>();
        piperonylbutoxideOtherNames.add("piperonylbutoxid");
        piperonylbutoxideOtherNames.add("piperonyl");
        allSubstances.add(new Substance("piperonylbutoxide", piperonylbutoxideOtherNames));

        Substance result = allSubstances
                .stream()
                .filter(substance -> {
                    if (substance.getName().toUpperCase().equals(substanceNameQuery.toUpperCase())) {
                        return true;
                    } else {
                        if (substance.getOtherNames() == null) {
                            return false;
                        }
                        String otherName = substance.getOtherNames().stream()
                                .filter(o -> substanceNameQuery.toUpperCase().equals(o.toUpperCase()))
                                .findFirst().orElse(null);

                        if (otherName == null) {
                            return false;
                        }
                        return otherName.toUpperCase().equals(substanceNameQuery.toUpperCase());
                    }
                }).findFirst()
                .orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("phenthoate", result.getName());

    }

    @Test
    void givenSubstanceOtherNameQueryThenFindSubstance() {

        String substanceNameQuery = "piperonyl";

        List<Substance> allSubstances = new ArrayList<>();
        Set<String> phenthoateOtherNames = new HashSet<>();
        phenthoateOtherNames.add("phenthoat");
        allSubstances.add(new Substance("phenthoate", phenthoateOtherNames));

        Set<String> piperonylbutoxideOtherNames = new HashSet<>();
        piperonylbutoxideOtherNames.add("piperonylbutoxid");
        piperonylbutoxideOtherNames.add("piperonyl");
        allSubstances.add(new Substance("piperonylbutoxide", piperonylbutoxideOtherNames));

        Substance result = allSubstances
                .stream()
                .filter(substance -> {
                    if (substance.getName().toUpperCase().equals(substanceNameQuery.toUpperCase())) {
                        return true;
                    } else {
                        if (substance.getOtherNames() == null) {
                            return false;
                        }
                        String otherName = substance.getOtherNames().stream()
                                .filter(o -> substanceNameQuery.toUpperCase().equals(o.toUpperCase()))
                                .findFirst().orElse(null);

                        if (otherName == null) {
                            return false;
                        }
                        return otherName.toUpperCase().equals(substanceNameQuery.toUpperCase());
                    }
                }).findFirst()
                .orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("piperonylbutoxide", result.getName());

    }

}