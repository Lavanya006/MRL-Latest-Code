package com.mt_ag.bayer.cmc.core.mapping;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.mt_ag.bayer.cmc.constants.SystemConstants;
import com.mt_ag.bayer.cmc.core.service.notification.HazardDetection;
import com.mt_ag.bayer.cmc.persistence.entity.*;
import com.mt_ag.bayer.cmc.persistence.repository.CountryRepository;
import com.mt_ag.bayer.cmc.persistence.repository.CropRepository;
import com.mt_ag.bayer.cmc.persistence.repository.DataSourcesRepository;
import com.mt_ag.bayer.cmc.persistence.repository.SubstanceRepository;
import com.mt_ag.bayer.cmc.persistence.service.UnitService;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UKMapper implements DataSourceMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UKMapper.class);

    @Autowired
    private CropRepository cropRepository;
    @Autowired
    private SubstanceRepository substanceRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private UnitService unitService;
    @Autowired
    private DataSourcesRepository dataSourceService;
    @Autowired
    private WebClientFactory webClientFactory;

    private GrabbingJob grabbingJob;

    @Override
    public Set<Measurement> map(GrabbingJob job, Substance substance, InputStream dataSourceInputStream, String dataSourceInfo) {
        this.grabbingJob = job;
        return (Set<Measurement>) grabber.grab(job.getDataSource(), substance);
    }

    private List<String> fetchAllOdsLinks() {
        List<String> odsLinks = new ArrayList<>();
        DataSource dataSource = this.dataSourceService.findByName_IgnoreCaseContaining(DataSource.UK_DATA_SOURCE);
        WebClient webClient = webClientFactory.create();
        try {
            HtmlPage page = webClient.getPage(dataSource.getUrl());

            List<HtmlTable> htmlTables = page.getByXPath(SystemConstants.UK_TABLE_ODS_LINKS);
            if(htmlTables.size() > 0) {
            HtmlTable dataFilesTable = htmlTables.get(SystemConstants.FIRST_ELEMENT);

            List<HtmlAnchor> dataFilesLinks = dataFilesTable.getByXPath(SystemConstants.UK_DOWNLOD_ODS_LINK);

            for (HtmlAnchor a : dataFilesLinks) {
                String link = a.getAttribute(SystemConstants.HREF);
                odsLinks.add(link);
                LOGGER.info(link);
            }
            }
            webClient.close();
        } catch (Exception e) {
            LOGGER.error(SystemConstants.UK_ERROR_MESSAGE_LINKS_ODS, e);
        } finally {
            webClient.close();
        }
        return odsLinks;
    }

    private Grabber grabber = (dataSource, substance) -> {
        Pattern substancePattern = Pattern.compile(SystemConstants.UK_SUBSTANCE_REGEX);
        Pattern detectedPattern = Pattern.compile(SystemConstants.UK_DETECTED_REGEX);
        Pattern mrlPattern = Pattern.compile(SystemConstants.UK_MRL_REGEX);
        LinkedHashSet<Measurement> measurements = new LinkedHashSet<Measurement>();
        this.fetchAllOdsLinks().parallelStream().forEach(odsLink -> {
            WebClient webClient = webClientFactory.create();
            try {
                SpreadsheetDocument doc = null;

                Page ods = null;
                try {
                    ods = webClient.getPage(odsLink);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (InputStream odsInputStream = ods.getWebResponse().getContentAsStream()) {
                    doc = SpreadsheetDocument.loadDocument(odsInputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try (InputStream odsInputStream = ods.getWebResponse().getContentAsStream()) {
                    doc = SpreadsheetDocument.loadDocument(odsInputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (doc == null) {
                    LOGGER.debug(odsLink + " could not be loaded");
                }

                for (Table t : doc.getTableList()) {
                    String tableName = t.getTableName();

                    if (tableName.endsWith(SystemConstants.UK_ODS_ENDING_SUM)) {
                        continue;
                    } else if (tableName.endsWith(SystemConstants.UK_ODS_ENDING_BNA)) {
                        String cropName = tableName.replaceAll(SystemConstants.UK_ODS_ENDING_BNA, "");

                        for (Row row : t.getRowList()) {
                            if (row.getCellByIndex(1).getDisplayText().contentEquals(SystemConstants.UK_DATE_OF_SAMPLING)
                                    | row.getCellByIndex(1).getDisplayText().isEmpty()
                                    | row.getCellByIndex(8).getDisplayText().startsWith(SystemConstants.UK_NONE)) {
                                continue;
                            }

                            String originCountryName = row.getCellByIndex(3).getDisplayText();
                            if (originCountryName.trim().isEmpty()) {
                                continue;
                            }

                            Measurement measurement = new Measurement();

                            String substanceName = "";
                            String detectedStr = "0.0";
                            Double detected = 0.0;
                            String mrlStr = "0.0";
                            Double mrl = 0.0;

                            String measurementInput = row.getCellByIndex(8).getDisplayText().replaceAll(SystemConstants.SUM, "");
                            if (measurementInput == null) {
                                continue;
                            } else if (measurementInput.trim().isEmpty()) {
                                continue;
                            }

                            Matcher substanceMatcher = substancePattern.matcher(measurementInput);
                            if (substanceMatcher.find()) {
                                substanceName = substanceMatcher.group(0);
                                if (substanceName.trim().isEmpty()) {
                                    continue;
                                }

                            } else {
                                continue;
                            }

                            Matcher detectedMatcher = detectedPattern.matcher(measurementInput);
                            if (detectedMatcher.find()) {
                                detectedStr = detectedMatcher.group(1);
                                if (detectedStr.trim().isEmpty()) {
                                    continue;
                                }
                            }
                            if (!detectedStr.isEmpty()) {
                                detected = Double.parseDouble(detectedStr);
                            }

                            Matcher mrlMatcher = mrlPattern.matcher(measurementInput);
                            if (mrlMatcher.find()) {
                                mrlStr = mrlMatcher.group(1);
                            }
                            if (!mrlStr.isEmpty()) {
                                mrl = Double.parseDouble(mrlStr);
                            }

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SystemConstants.DATE_FORMAT_STRING);
                            LocalDateTime samplingDate = LocalDateTime.of(
                                    LocalDate.parse(row.getCellByIndex(1).getDisplayText(), formatter),
                                    LocalTime.of(12, 0, 0));

                            measurement.setMrl(mrl);
                            measurement.setResidueLevel(detected);

                            cropName = cropName.replace("_", " ");
                            Crop crop = this.cropRepository.findByNameIgnoreCaseEquals(cropName);
                            if (crop == null) {
                                List<Crop> found = this.cropRepository.findByOtherNamesIgnoreCaseEquals(cropName);
                                if (found != null) {
                                    if (found.size() > 0) {
                                        crop = found.get(0);
                                    }
                                }
                            }
                            if (crop != null) {
                                measurement.setCrop(crop);
                            } else {
                                LOGGER.debug("CROP: " + cropName + SystemConstants.NOT_FOUND_IN_MASTER_DATA);
                                continue;
                            }

                            Substance sub = fetchSubstance(substanceName);

                            if (sub == null) {
                                List<Substance> found = this.substanceRepository
                                        .findByOtherNamesIgnoreCaseEquals(substanceName);
                                if (found != null) {
                                    if (found.size() > 0) {
                                        sub = found.get(0);
                                    }
                                }
                            }
                            if (sub != null) {
                                measurement.setSubstance(sub);
                            } else {
                                LOGGER.debug("SUBSTANCE: " + substanceName + SystemConstants.NOT_FOUND_IN_MASTER_DATA);
                                continue;
                            }

                            Country origin = fetchOriginCountry(originCountryName);
                            measurement.setOriginCountry(origin);
                            measurement.setSamplingCountry(fetchDefaultSamplingCountry());
                            measurement.setUnit(fetchDefaultUnit());
                            measurement.setSamplingDate(samplingDate);
                            measurement.setGrabbingJob(grabbingJob);
                            measurement.setMrlsign(HazardDetection.whatLevel(measurement));

                            if (measurement.getMrl() > 0) {
                                LOGGER.info("Ignoring Measurement with MRL 0: {}", measurement.toString());
                                measurements.add(measurement);
                            }
                        }
                    } else {
                        continue;
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Grabbing ERROR", e);
            } finally {
                webClient.close();
            }
        });


        //System.out.println("UK Mesurement");
        LOGGER.info("UK Measurements grabbed: " + measurements.size());
        return measurements;
    };

    private Country fetchOriginCountry(String name) {
        if (this.countryRepository == null) {
            return null;
        }
        if (name == null) {
            return null;
        }
        Country country = this.countryRepository.findByNameIgnoreCaseEquals(name);
        if (country == null) {
            List<Country> result = this.countryRepository.findByOtherNamesIgnoreCaseEquals(name);
            if (result != null) {
                if (!result.isEmpty()) {
                    country = result.get(0);
                    if (result.size() > 1) {
                        String[] resultIds = {""};
                        result.forEach(c -> resultIds[0] += c.getId() + " | ");
                        LOGGER.warn("The Country Name | " + name
                                + " | is returning "
                                + result.size()
                                + " results in the search by other names. This is due to same name with different case. Othername IDs: "
                                + resultIds[0]);
                    }
                }
            }
        }

        if (country == null) {
            LOGGER.warn("COUNTRY | " + name + " | not found in Master Data");
        }
        return country;
    }

    private Substance fetchSubstance(String name) {
        if (this.substanceRepository == null) {
            return null;
        }
        if (name == null) {
            return null;
        }
        Substance substance = this.substanceRepository.findByNameIgnoreCaseEquals(name);
        if (substance == null) {
            List<Substance> result = this.substanceRepository.findByOtherNamesIgnoreCaseEquals(name);
            if (result != null) {
                if (!result.isEmpty()) {
                    substance = result.get(0);
                    if (result.size() > 1) {
                        String[] resultIds = {""};
                        result.forEach(s -> resultIds[0] += s.getId() + " | ");
                        LOGGER.warn("The Substance Name | " + name
                                + " | is returning "
                                + result.size()
                                + " results in the search by other names. This is due to same name with different case. Othername IDs: "
                                + resultIds[0]);
                    }
                }
            }
        }
        if (substance == null) {
            LOGGER.warn("SUBSTANCE | " + name + " | not found in Master Data");
        }
        return substance;
    }

    private Unit fetchDefaultUnit() {
        return this.unitService.findByName(SystemConstants.MG_KG);
    }

    private Country fetchDefaultSamplingCountry() {
        return this.countryRepository.findByNameIgnoreCaseEquals(SystemConstants.UK_UNITED_KINGDOM);
    }
}