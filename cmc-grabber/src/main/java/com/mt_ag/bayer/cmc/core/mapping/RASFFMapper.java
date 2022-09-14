package com.mt_ag.bayer.cmc.core.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.mt_ag.bayer.cmc.constants.SystemConstants;
import com.mt_ag.bayer.cmc.core.mapping.entity.FollowUp;
import com.mt_ag.bayer.cmc.core.mapping.entity.Hazard;
import com.mt_ag.bayer.cmc.core.mapping.entity.RASFFMapping;
import com.mt_ag.bayer.cmc.core.service.RASFFGrabberService;
import com.mt_ag.bayer.cmc.core.service.notification.HazardLevel;
import com.mt_ag.bayer.cmc.persistence.entity.*;
import com.mt_ag.bayer.cmc.persistence.repository.CropRepository;
import com.mt_ag.bayer.cmc.persistence.repository.MrlRepository;
import com.mt_ag.bayer.cmc.persistence.service.CountryService;
import com.mt_ag.bayer.cmc.persistence.service.GrabbingJobService;
import com.mt_ag.bayer.cmc.persistence.service.UnitServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

@Service
public class RASFFMapper implements DataSourceMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(RASFFGrabberService.class);

    @Autowired
    private CountryService countryService;
    @Autowired
    private CropRepository cropService;
    @Autowired
    private UnitServiceImpl unitService;
    @Autowired
    private GrabbingJobService jobService;
    @Autowired
    private MrlRepository mrlRepository;

    private LocalDateTime lastExecution;
    private Set<String> allCountryNames;

    @Autowired
    private WebClientFactory webClientFactory;

    @Override
    public Set<Measurement> map(GrabbingJob job, Substance substance, InputStream dataSourceInputStream, String dataSourceInfo) {
        this.allCountryNames = this.countryService.findAll().stream().map(Country::getName).collect(Collectors.toSet());
        this.lastExecution = this.jobService.getLastExecution(job);

        Set<RASFFMapping> grabbedRASFFData = (Set<RASFFMapping>) grabber.grab(job.getDataSource(), substance);
        Set<Measurement> measurements = grabbedRASFFData.parallelStream()
                .map(rasffMapping -> this.initMeasurement(rasffMapping, substance, job))
                .filter(m -> {
                            if (m.getMrl() == null) {
                                return false;
                            }
                            if (m.getMrl() == 0) {
                                LOGGER.info("Ignoring Measurement with MRL 0: {}", m.toString());
                                return false;
                            }
                            return (m.getCrop() != null
                                    && m.getUnit() != null
                                    && m.getOriginCountry() != null
                                    && m.getResidueLevel() != null);
                        }
                ).collect(Collectors.toSet());

        LOGGER.debug(substance.getName() + ": " + measurements.size() + " mapped / " + grabbedRASFFData.size() + " grabbed");
        return measurements;
    }

    Grabber grabber = (dataSource, substance) -> {
        LinkedHashSet<RASFFMapping> grabbedRASFFData = new LinkedHashSet<>();

        if (webClientFactory == null) {
            webClientFactory = new WebClientFactory();
        }
        
        WebClient webClient = webClientFactory.create();

        HtmlPage page;
        try {
        	
        	//webClient.getOptions().setCssEnabled(false);
        	webClient.getOptions().setJavaScriptEnabled(true);
        	webClient.getOptions().setThrowExceptionOnScriptError(false);
        	//webclient.getOptions().sleep();
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	//webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        	//webClient.getOptions().setTimeout(100000);
        	
        	//Thread.sleep(3_000);
        //	Thread.sleep(1000);
			
         //   page = webClient.getPage(dataSource.getUrl());
            page = webClient.getPage("https://webgate.ec.europa.eu/rasff-window/screen/search");
      
            //System.out.println(page.getUrl());
        } 
        
        catch (IOException e) {
            LOGGER.warn("Could not read the RASFF Data Source URL: " + dataSource.getUrl());
            webClient.close();
            return new LinkedHashSet<>();
        }

        webClient.waitForBackgroundJavaScript(10000 * 2);
       // HtmlButton button=page.getFirstByXPath("//*[@id=loader]");
      //  HtmlDivision div = page.getFirstByXPath("//div[@class='col-xl-2 col-lg-2 cig-middle']/button");
        //HtmlButton div = page.getFirstByXPath("//[@id='\\Search\\']//ancestor::div[@class='col-xl-2 col-lg-2 cig-middle']");
      //  HtmlButton button=page.getFirstByXPath("//button[@type='submit']");
        //HtmlButton button=page.querySelector("button[type='submit']");
        //System.out.println(page.querySelector("button[type='submit']"));
       
        
    //    HtmlButton button = (HtmlButton)page.getByXPath("./html/body/app-root/ux-layout-app-shell/div/div/div/app-search-component/div/form/div[3]/div[4]/button") ; 
       // List<DomElement> Buttonlist = page.getByXPath("./html/body/app-root/ux-layout-app-shell/div/div/div/app-search-component/div/form/div[3]/div[4]/button") ; 
        
 //       List<HtmlElement> Buttonlist= (List<HtmlElement>) page.getByXPath("./html/body/app-root/ux-layout-app-shell/div/div/div/app-search-component/div/form/div[3]/div[4]/button").get(0); 
       // HtmlButton button=(HtmlButton) Buttonlist.get(0);
        
         // HtmlButton button=(HtmlButton)page.getByXPath("//input[@type='submit'].get(0)");
       // HtmlTextInput htmlTextInput = page.getHtmlElementById(SystemConstants.RASFF_KEYWORD_ID);
        //htmlTextInput.setText(substance.getName());
       // HtmlAnchor anchorForSearch = page.getAnchorByText(SystemConstants.RASFF_SEARCH_KEYWORD_CLICK_ID);
       // HtmlPage pagedetails=page.getPage();
        
    //   List<DomElement> buttonElement = page.getByXPath("./html/body/app-root/ux-layout-app-shell/div/div/div/app-search-component/div/form/div[3]/div[4]/button");
      //  List<?> button=page.getByXPath("//button[@type='submit']", null);
      //    String button=page.getNamespaceURI();
       // List<HtmlForm> Pageform = (List<HtmlForm>) page. ().get(0);
     //   System.out.println(pagsXml());
        //System.out.println(page.asText());
       // System.out.println(page.asXml());
       DomElement allElement = (DomElement) page.getDocumentElement();
       DomNodeList<DomNode> button2 = allElement.querySelectorAll("button");
    //    HtmlAnchor buttonLink = (HtmlAnchor) buttonElement.get(SystemConstants.FIRST_ELEMENT);
        
        HtmlPage pageSearch = null;
        try {
            pageSearch = ((DomElement) button2).click();
        } catch (IOException e) {
            LOGGER.warn("Could not click anchorForSearch:" + button2);
            webClient.close();
            return new LinkedHashSet<>();
        }

        int resultsCount = extractResultsHeader(pageSearch);
        boolean doNextPage = true;

        if (resultsCount > 0) {

            List<HtmlTableRow> tableResultRows = new ArrayList<HtmlTableRow>();

            do {
                HtmlTable tableResult = pageSearch.getHtmlElementById(SystemConstants.RASFF_RESULT_TABLE_NAME);

                tableResultRows.addAll(tableResult.getRows());

                if (doNextPage) {
                    List<DomElement> domNext = pageSearch.getByXPath(SystemConstants.RASFF_NEXT_PAGE_XPATH);
                    if (!domNext.isEmpty()) {
                        doNextPage = false;
                    } else {
                        HtmlAnchor anchorNextPage = pageSearch
                                .getAnchorByText(SystemConstants.RASFF_NEXT_PAGE_CLICK_ID);
                        try {
                            pageSearch = anchorNextPage.click();
                        } catch (IOException e) {
                            LOGGER.warn("Could not click anchorNextPage: " + anchorNextPage);
                            doNextPage = false;
                        }
                    }
                }
            } while (doNextPage);

            Set<RASFFMapping> details = tableResultRows.parallelStream().map(row -> getDetailResult(row, substance))
                    .filter(r -> !r.getHazards().isEmpty()).collect(Collectors.toSet());
            grabbedRASFFData.addAll(details);
        }
        webClient.close();
        return grabbedRASFFData;
    };

    RASFFMapping getDetailResult(HtmlTableRow tableResultRow, Substance substance) {
        RASFFMapping rasffMapping = new RASFFMapping();

        if (tableResultRow == null) {
            return rasffMapping;
        }

        List<HtmlTableCell> tableResultcells = tableResultRow.getCells();
        List<HtmlElement> linktemp = tableResultcells.get(SystemConstants.RASFF_DETAIL_CLICK_COLUMN_POSITION)
                .getElementsByTagName(SystemConstants.A);
        if (linktemp.isEmpty()) {
            return rasffMapping;
        }
        HtmlAnchor link = (HtmlAnchor) linktemp.get(SystemConstants.FIRST_ELEMENT);

        HtmlPage pageDetails = null;
        try {
            pageDetails = link.click();
        } catch (IOException e) {
            LOGGER.warn("Could not click the link: " + link);
        }

        List<DomElement> suspectElement = pageDetails.getByXPath(SystemConstants.RASFF_SUSPECT_XPATH);
        String suspect = suspectElement.get(SystemConstants.FIRST_ELEMENT).asText();

        int indexer = 0;
        rasffMapping.setNextInfo(indexer++, this.cleanText(suspect));

        if (suspect != null) {
            // It must contain the whole name of the substance
            String regex = "\\b" + substance.getName().toUpperCase() + "\\b";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(suspect.toUpperCase());

            if (!matcher.find()) {
                // Discard this result
                return rasffMapping;
            }
            rasffMapping = extractOriginCountry(rasffMapping);
        }

        List<DomElement> domElementsInfoFields = pageDetails.getByXPath(SystemConstants.RASFF_INFO_FIELDS_XPATH);
        for (DomElement f : domElementsInfoFields) {
            if (indexer == 3) {
                // Update only on root and before mapping (updateOnly)
                // The Notification Date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SystemConstants.DATE_FORMAT_STRING);
                LocalDateTime samplingDate = LocalDateTime.of(LocalDate.parse(f.asText(), formatter),
                        LocalTime.of(12, 0, 0));

                if (lastExecution != null) {
                    if (samplingDate.isAfter(lastExecution)) {
                        rasffMapping.setNextInfo(indexer++, this.cleanText(f.asText()));
                    } else {
                        // Not interested in this Measurement because it has been already downloaded
                        // before.
                        // Try the next Measurement
                        rasffMapping.setProduct(null);
                        return rasffMapping;
                    }
                } else {
                    // This is the first time we save this.
                    rasffMapping.setNextInfo(indexer++, this.cleanText(f.asText()));
                }
            } else {
                rasffMapping.setNextInfo(indexer++, this.cleanText(f.asText()));
            }
        }

        try {
            HtmlTable tableHazard = pageDetails.getHtmlElementById(SystemConstants.RASFF_HAZARD_TABLE_NAME);
            try {
                Hazard hazardEntry = new Hazard();
                for (int j = 1; j < tableHazard.getRows().size(); j++) {
                    hazardEntry = new Hazard();
                    indexer = 0;
                    for (HtmlTableCell cell : tableHazard.getRows().get(j).getCells()) {
                        String value = this.cleanText(cell.asText());
                        if (indexer == 3) {
                            if (value == null | value.isEmpty()) {
                                // The Unit
                                // Discarding this result since it has no measurement
                                return rasffMapping;
                            }
                        }
                        hazardEntry.setNextHazardValue(indexer++, value);
                    }
                    rasffMapping.addNextHazard(hazardEntry);
                }
            } catch (Exception e) {
                LOGGER.error("Hazard table error --skipped", e);
                return rasffMapping;
            }
        } catch (ElementNotFoundException enf) {

            try {
                List<DomElement> domElementsFollowUps = pageDetails
                        .getByXPath(SystemConstants.RASFF_FOLLOW_UP_TABLE_XPATH);
                HtmlTable tableFollowUps = (HtmlTable) domElementsFollowUps.get(SystemConstants.FIRST_ELEMENT);
                indexer = 0;
                for (HtmlTableRow row : tableFollowUps.getRows()) {
                    FollowUp followEntry = new FollowUp();
                    for (HtmlTableCell cell : row.getCells()) {
                        followEntry.setNextFollowUpValue(indexer++, this.cleanText(cell.asText()));
                    }
                    rasffMapping.addNextFollowUp(followEntry);
                }
            } catch (Exception e) {
                LOGGER.error("Follow ups error --skipped", e);
                return rasffMapping;
            }
        }
        return rasffMapping;
    }

    Measurement initMeasurement(RASFFMapping rasffMapping, Substance substance, GrabbingJob job) {
        Measurement measurement = new Measurement();
        if (rasffMapping.getProduct() == null) {
            measurement.setCrop(null);
            return measurement;
        }
        // here get date and check if should proceed
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SystemConstants.DATE_FORMAT_STRING);
            LocalDateTime samplingDate = LocalDateTime
                    .of(LocalDate.parse(rasffMapping.getNotificationDate(), formatter), LocalTime.of(12, 0, 0));

            measurement.setSamplingDate(samplingDate);

        } catch (Exception e) {
            LOGGER.error("Error while parsing SamplingDate: " + rasffMapping.getNotificationDate(), e);
        }

        measurement.setSubstance(substance);
        measurement.setGrabbingJob(job);
        measurement.setMrlsign(HazardLevel.RED);

        if (rasffMapping.getHazards() != null) {
            if (measurement.getOptionalData() == null) {
                measurement.setOptionalData(new HashMap<String, String>());
            }
            for (Hazard hazard : rasffMapping.getHazards()) {
                if (hazard.getSubtance().contains(substance.getName())) {
                    String cleanName = hazard.getSubtance()
                            .replace(SystemConstants.RASFF_UNAUTHORISED_SUBSTANCE_STRING, "")
                            .replace(SystemConstants.RASFF_PROHIBITED_SUBSTANCE_STRING, "").trim();
                    if (!cleanName.equalsIgnoreCase(substance.getName())) {
                        measurement.getOptionalData().put(SystemConstants.RASFF_SOURCE_SUBSTANCE_NAME_STRING,
                                cleanName);
                    }
                }
            }
        }

        Country origin = null;
        Country destination = null;

        if (rasffMapping.getConcernedCountriesAndOrganisations() != null) {
            String originCountryName = null;

            originCountryName = tryWithFirstOriginCountry(rasffMapping);

            if (originCountryName == null) {
                originCountryName = tryWithConcernedCountries(rasffMapping);
            }

            if (originCountryName != null) {
                if (this.countryService == null) {
                    origin = new Country(originCountryName, new HashSet<>());
                } else {
                    origin = this.countryService.findByName(originCountryName);
                }
                if (origin == null) {
                    List<Country> foundOrigin = this.countryService.findByOtherNames(originCountryName);
                    if (foundOrigin != null) {
                        if (foundOrigin.size() > 0) {
                            origin = foundOrigin.get(0);
                        }
                    }
                }
            }
        }

        if (rasffMapping.getNotificationFrom() != null) {
            String destinationCountryName = this.cleanCountryName(rasffMapping.getNotificationFrom());
            if (this.countryService == null) {
                destination = new Country(destinationCountryName, new HashSet<>());
            } else {
                destination = this.countryService.findByName(destinationCountryName);
            }

            if (destination == null) {
                List<Country> foundDestination = this.countryService
                        .findByOtherNames(destinationCountryName);
                if (foundDestination != null) {
                    if (foundDestination.size() > 0) {
                        destination = foundDestination.get(0);
                    }
                }
            }
        }

        measurement.setOriginCountry(origin);
        measurement.setSamplingCountry(destination);
        measurement.setCrop(this.initCrop(rasffMapping));

        Hazard hazardOfInterest = null;
        if (rasffMapping.getHazards() != null) {
            for (Hazard h : rasffMapping.getHazards()) {
                if (h.getSubtance().toUpperCase().contains(substance.getName().toUpperCase())) {
                    hazardOfInterest = h;
                }
            }
        }

        if (hazardOfInterest != null) {
            if (hazardOfInterest.getAnalyticalResults() != null
                    && hazardOfInterest.getAnalyticalResults().size() == 1) {
                measurement.setResidueLevel(hazardOfInterest.getAnalyticalResults().iterator().next());
            }

            if (hazardOfInterest.getUnits() != null) {
                if (this.unitService == null) {
                    measurement.setUnit(new Unit(SystemConstants.MG_KG));
                } else {
                    measurement.setUnit(this.unitService.findByName(SystemConstants.MG_KG));
                }

            }
        }

        // Getting MRL
        if (measurement.getCrop() != null) {
            MRL found = null;
            //FIXME: Get rid of this expensive database query
            if (this.mrlRepository != null) {
                found = this.mrlRepository.findBySubstanceNameIgnoreCaseEqualsAndMrlProducts_ProductNameIgnoreCaseEquals(
                        measurement.getSubstance().getName(), measurement.getCrop().getName());

            }
            if (found != null) {
                MrlProduct innerFound = found
                        .getproductWithname(measurement.getCrop().getName());
                if (innerFound != null) {
                    measurement.setMrl(innerFound.getMrl());
                } else {
                    measurement.setMrl(SystemConstants.DEFAULT_MRL_VALUE);
                }

            } else {
                measurement.setMrl(SystemConstants.DEFAULT_MRL_VALUE);
            }
        }
        return measurement;
    }

    private Crop initCrop(RASFFMapping rasffMapping) {
        Crop crop = null;
        if (this.cropService == null) {
            crop = new Crop(null, rasffMapping.getProduct(), new HashSet<>());
        } else {
            crop = this.cropService.findByNameIgnoreCaseEquals(rasffMapping.getProduct());
        }
        if (crop == null) {
            List<Crop> found = this.cropService.findByOtherNamesIgnoreCaseEquals(rasffMapping.getProduct());
            if (found != null) {
                if (found.size() > 0) {
                    crop = found.get(0);
                }
            }
        }
        return crop;
    }

    private String tryWithFirstOriginCountry(RASFFMapping rasffMapping) {
        return rasffMapping.getFirstOriginCountry();
    }

    private String tryWithConcernedCountries(RASFFMapping rasffMapping) {
        if (rasffMapping.getConcernedCountriesAndOrganisations() != null) {
            for (String o : rasffMapping.getConcernedCountriesAndOrganisations()) {
                if (o.contains(SystemConstants.RASFF_ORIGIN_COUNTRY_STRING)) {
                    return this.cleanCountryName(o);
                }
            }
        }
        return null;
    }

    private RASFFMapping extractOriginCountry(RASFFMapping rasffMapping) {
        if (this.allCountryNames == null) {
            return rasffMapping;
        }
        String suspect = rasffMapping.getSuspect();
        for (String country : this.allCountryNames) {
            String fromRegex = "(?i)\\bfrom\\b.*?\\b" + country + "\\b.*?";
            String countryRegex = "\\b" + country + "\\b";

            Pattern fromPattern = Pattern.compile(fromRegex);
            Matcher fromMatcher = fromPattern.matcher(suspect);

            Pattern countryPattern = Pattern.compile(countryRegex);
            Matcher countryMatcher = countryPattern.matcher(suspect);

            boolean isFrom = fromMatcher.find();

            if (isFrom) {
                boolean found = countryMatcher.find();
                if (found) {
                    rasffMapping.setFirstOriginCountry(country);
                    return rasffMapping;
                }
            } else {
                for (String retryCountry : this.allCountryNames) {
                    String retryCountryUpper = retryCountry.toUpperCase();
                    if (retryCountryUpper.contains(",")) {
                        if (suspect.toUpperCase()
                                .contains(retryCountryUpper.substring(0, retryCountryUpper.indexOf(',')))) {
                            rasffMapping.setFirstOriginCountry(retryCountry);
                            return rasffMapping;
                        }
                    }
                }
            }
        }
        return rasffMapping;
    }

    private int extractResultsHeader(HtmlPage page) {
        HtmlElement resultsMainContent = page.getHtmlElementById(SystemConstants.RASFF_MAIN_CONTENT);
        HtmlElement resultsHeader = resultsMainContent.getElementsByTagName(SystemConstants.H1)
                .get(SystemConstants.FIRST_ELEMENT);
        int resultsCount = this.extractResultsCount(resultsHeader.asXml());
        return resultsCount;
    }

    private int extractResultsCount(String htmlElement) {
        Pattern pattern = Pattern.compile(SystemConstants.RASFF_RESULTS_COUNT_REGEX);
        Matcher matcher = pattern.matcher(htmlElement);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            return 0;
        }
    }

    private String cleanText(String text) {
        String cleanText = text.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "")
                .trim();
        return cleanText;
    }

    private String cleanCountryName(String countryName) {
        return cleanText(countryName.replaceAll("\\s*\\([^\\)]*\\)\\s*", " "));
    }

    private String cleanUnits(String unitstring) {

        String cleanedUnit = unitstring;

        if (unitstring.contains("-")) {

            if (unitstring.contains("ppb")) {
                cleanedUnit = unitstring.replaceAll("- ppb", "");
            } else if (unitstring.contains("ppm")) {
                cleanedUnit = unitstring.replaceAll("- ppm", "");
            }
        }

        return cleanText(cleanedUnit);
    }

    private Set<Double> convertAllValuesToUnitInMgKg(Set<Double> values, String unit) {

        Iterator<Double> iterateValues = values.iterator();
        Set<Double> convertedVales = new HashSet<Double>();

        while (iterateValues.hasNext()) {
            double value = iterateValues.next();
            double unitInMGKG;
            // convert

            switch (unit) {
                case "per": // mg/kg↔per 1 per = 10000 mg/kg
                    unitInMGKG = value / 10000;
                    break;

                case "ppm": // mg/kg↔ppm 1 mg/kg = 1 ppm
                    unitInMGKG = value;
                    break;

                case "ppb": // mg/kg↔ppb 1 mg/kg = 1000 ppb
                    unitInMGKG = value / 1000;
                    break;

                case "ppt": // mg/kg↔ppt 1 mg/kg = 1000000 ppt
                    unitInMGKG = value / 1000000;
                    break;

                case "ppq": // mg/kg↔ppq 1 mg/kg = 1000000000 ppq
                    unitInMGKG = value / 1000000000;
                    break;

                case "g/kg": // mg/kg↔g/kg 1 g/kg = 1000 mg/kg
                    unitInMGKG = value * 1000;
                    break;

                case "ug/g": // mg/kg↔ug/g 1 mg/kg = 1 ug/g
                    unitInMGKG = value;
                    break;

                case "μg/g": // mg/kg↔ug/g 1 mg/kg = 1 ug/g
                    unitInMGKG = value;
                    break;

                case "ug/kg": // mg/kg↔ug/kg 1 mg/kg = 1000 ug/kg
                    unitInMGKG = value / 1000;
                    break;

                case "μg/kg": // mg/kg↔ug/kg 1 mg/kg = 1000 ug/kg
                    unitInMGKG = value / 1000;
                    break;

                case "ng/ug": // mg/kg↔ng/ug 1 ng/ug = 1000 mg/kg
                    unitInMGKG = value * 1000;
                    break;

                case "ng/g": // mg/kg↔ng/g 1 mg/kg = 1000 ng/g
                    unitInMGKG = value / 1000;
                    break;

                case "mg/g": // mg/kg↔mg/g 1 mg/g = 1000 mg/kg
                    unitInMGKG = value * 1000;
                    break;

                case "ul/l": // mg/kg↔ul/l 1 mg/kg = 1 ul/l
                    unitInMGKG = value;
                    break;

                case "nl/ml": // mg/kg↔nl/ml 1 nl/ml = 1000 mg/kg
                    unitInMGKG = value * 1000;
                    break;

                case "ml/m3": // mg/kg↔ml/m3 1 mg/kg = 1 ml/m3
                    unitInMGKG = value;
                    break;

                case "ml/m³": // mg/kg↔ml/m3 1 mg/kg = 1 ml/m3
                    unitInMGKG = value;
                    break;

                case "ml/l": // mg/kg↔ml/l 1 ml/l = 1000 mg/kg
                    unitInMGKG = value * 1000;
                    break;

                case "ul/m3": // mg/kg↔ul/m3 1 mg/kg = 1000 ul/m3
                    unitInMGKG = value / 1000;
                    break;

                case "ul/m": // mg/kg↔ul/m3 1 mg/kg = 1000 ul/m3
                    unitInMGKG = value / 1000;
                    break;

                case "ug/mg": // mg/kg↔ug/mg 1 ug/mg = 1000 mg/kg
                    unitInMGKG = value * 1000;
                    break;

                case "g/ton": // mg/kg↔g/ton 1 mg/kg = 1 g/ton
                    unitInMGKG = value;
                    break;

                case "mg/dl": // mg/kg↔mg/dl 1 mg/kg = 1 mg/dl
                    unitInMGKG = value;
                    break;

                case "g/100g": // mg/kg↔g/100g 10000 mg/kg = 1 g/100g
                    unitInMGKG = value * 10000;
                    break;

                default: // unknown
                    unitInMGKG = 0;
            }

            convertedVales.add(unitInMGKG);
        }

        return convertedVales;
    }

}
