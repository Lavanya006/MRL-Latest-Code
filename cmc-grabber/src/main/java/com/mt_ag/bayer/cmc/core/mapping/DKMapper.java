package com.mt_ag.bayer.cmc.core.mapping;

import com.mt_ag.bayer.cmc.core.mapping.entity.DKGrabbingModel;
import com.mt_ag.bayer.cmc.core.service.notification.HazardLevel;
import com.mt_ag.bayer.cmc.persistence.entity.*;
import com.mt_ag.bayer.cmc.persistence.repository.CountryRepository;
import com.mt_ag.bayer.cmc.persistence.repository.CropRepository;
import com.mt_ag.bayer.cmc.persistence.repository.SubstanceRepository;
import com.mt_ag.bayer.cmc.persistence.repository.UnitRepository;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DKMapper implements DataSourceMapper {

    private static final Logger logger = LoggerFactory.getLogger(DKMapper.class);
    public static final String FIRST_QUARTER = "1";
    public static final String SECOND_QUARTER = "2";
    public static final String THIRD_QUARTER = "3";
    public static final String FOURTH_QUARTER = "4";
    public static final int MIDDLE_MONTH_OF_FIRST_QUARTER = 2;
    public static final int MIDDLE_MONTH_OF_SECOND_QUARTER = 5;
    public static final int MIDDLE_MONTH_OF_THIRD_QUARTER = 8;
    public static final int MIDDLE_MONTH_OF_FOURTH_QUARTER = 11;
    public static final int MIDDLE_DAY_OF_THE_MONTH = 15;
   // public static final String SPREADSHEET_LINK_BEGIN = "https://www.food.dtu.dk/english/-/media/Institutter/Foedevareinstituttet/Publikationer/Pub-";
    public static final String SPREADSHEET_LINK_BEGIN = "https://www.food.dtu.dk/english/-/media/institutter/foedevareinstituttet/publikationer/pub-";

    
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CropRepository cropRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private SubstanceRepository substanceRepository;

    @Override
    public Set<Measurement> map(GrabbingJob job, Substance substance, InputStream dataSourceInputStream, String dataSourceInfo) {
        Set<Measurement> measurements = new HashSet<>();

        HSSFWorkbook hssfWorkbook = null;
        try {
        	hssfWorkbook = new HSSFWorkbook(dataSourceInputStream);
        } catch (IOException e) {
        	hssfWorkbook = null;
        }

        if (hssfWorkbook == null) {
            logger.info("skipping processing of non XLS file: " + dataSourceInfo);
            return measurements;
        }

        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 0; rowNum < hssfSheet.getLastRowNum(); rowNum++) {
                DKGrabbingModel grabbingModel = new DKGrabbingModel();
                grabbingModel.setSamplingDate(computeDefaultSamplingDate(dataSourceInfo));

                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    if (hssfRow.getCell(0) != null) {
                        if (!hssfRow.getCell(0).toString().equals("Marked")) {
                            continue;
                        }
                    }

                    Double currentRowMrl = 0.0;
                    Double currentRowResidueLevel = 0.0;

                    for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {
                        HSSFCell cell = hssfRow.getCell(cellNum);

                        if (hssfRow.getCell(1) == null) {
                            //Crop Country must not be null
                            break;
                        }

                        if (hssfRow.getCell(3) == null) {
                            //Origin Country must not be null
                            break;
                        }

                        if (hssfRow.getCell(5) == null) {
                            //Substance must not be null
                            break;
                        }

                        if (hssfRow.getCell(10) == null) {
                            //Measurement must not be null
                            break;
                        }

                        if (hssfRow.getCell(11) == null) {
                            //MRL must not be null
                            break;
                        }

                        if (cellNum == 1) {
                            grabbingModel.setCrop(cell.toString());
                        } else if (cellNum == 3) {
                            grabbingModel.setCountry(cell.toString().replaceAll("-", "").trim());
                        } else if (cellNum == 5) {
                            grabbingModel.setSubstance(cell.toString());
                        } else if (cellNum == 10) {
                            try {
                                currentRowResidueLevel = Double.parseDouble(cell.toString());
                            } catch (Exception e) {
                                currentRowResidueLevel = 0.0;
                            }
                            grabbingModel.setResidueLevel(currentRowResidueLevel);
                        } else if (cellNum == 11) {
                            try {
                                currentRowMrl = Double.parseDouble(cell.toString());
                            } catch (Exception e) {
                                currentRowMrl = 0.0;
                            }
                            grabbingModel.setMrl(currentRowMrl);
                        }
                    }
                }
                measurements.add(this.initMeasurement(job, substance, grabbingModel));
            }
        }
        logger.debug("Grabbed: " + measurements.size());
        return measurements;
    }

    LocalDateTime computeDefaultSamplingDate(String spreadsheetLink) {
        String baseString = spreadsheetLink.replaceAll(SPREADSHEET_LINK_BEGIN, "").trim();
        String year = baseString.substring(0, 4);
        String quarter = FIRST_QUARTER;

        if (baseString.contains("Pest")) {
            int yearBeginIndex = baseString.lastIndexOf("Pest") + 4;
            int yearEndIndex = yearBeginIndex + 4;
            year = baseString.substring(yearBeginIndex, yearEndIndex);
            try {
                Integer.parseInt(year);
            } catch (Exception e) {
                year = baseString.substring(0, 4);
            }

            if (baseString.contains("kvartal")) {
                int quarterBeginIndex = yearEndIndex + 1;
                int quarterEndIndex = quarterBeginIndex + 1;
                try {
                    quarter = baseString.substring(quarterBeginIndex, quarterEndIndex);
                } catch (Exception e) {
                    quarter = FIRST_QUARTER;
                    logger.warn("Could not extract quarter from " + spreadsheetLink + ". Falling to default quarter 1.");
                }

            }
        }
        return LocalDateTime.of(Integer.parseInt(year), middleMonthFromQuarter(quarter), MIDDLE_DAY_OF_THE_MONTH, 0, 0);
    }

    private int middleMonthFromQuarter(String quarter) {
        switch (quarter) {
            case FIRST_QUARTER:
                return MIDDLE_MONTH_OF_FIRST_QUARTER;
            case SECOND_QUARTER:
                return MIDDLE_MONTH_OF_SECOND_QUARTER;
            case THIRD_QUARTER:
                return MIDDLE_MONTH_OF_THIRD_QUARTER;
            case FOURTH_QUARTER:
                return MIDDLE_MONTH_OF_FOURTH_QUARTER;
            default:
                return MIDDLE_MONTH_OF_FIRST_QUARTER;
        }
    }

    private Measurement initMeasurement(GrabbingJob grabbingJob, Substance substance, DKGrabbingModel grabbingModel) {
        Unit unit = fetchUnit();
        HashMap<String, String> optionalData = null;
        LocalDateTime samplingDate = grabbingModel.getSamplingDate();
        Crop crop = fetchCrop(grabbingModel);
        Country originCountry = fetchOriginCountry(grabbingModel);
        Country samplingCountry = fetchSamplingCountry();
        Substance grabbedSubstance = fetchSubstance(grabbingModel);

        double residueLevel = grabbingModel.getResidueLevel();
        double mrl = grabbingModel.getMrl();
        int mrlSign = residueLevel > mrl ? HazardLevel.RED : HazardLevel.GREEN;
        return new Measurement(crop, originCountry, samplingCountry, grabbedSubstance,
                unit, grabbingJob, optionalData, samplingDate, residueLevel, mrl, mrlSign);
    }

    private Substance fetchSubstance(DKGrabbingModel grabbingModel) {
        if (this.substanceRepository == null) {
            return null;
        }
        if (grabbingModel.getSubstance() == null) {
            return null;
        }
        Substance substance = this.substanceRepository.findByNameIgnoreCaseEquals(grabbingModel.getSubstance());
        if (substance == null) {
            List<Substance> result = this.substanceRepository.findByOtherNamesIgnoreCaseEquals(grabbingModel.getSubstance());
            if (result != null) {
                if (!result.isEmpty()) {
                    substance = result.get(0);
                    if (result.size() > 1) {
                        String[] resultIds = {""};
                        result.forEach(s -> resultIds[0] += s.getId() + " | ");
                        logger.warn("The Substance Name | " + grabbingModel.getSubstance()
                                + " | is returning "
                                + result.size()
                                + " results in the search by other names. This is due to same name with different case. Othername IDs: "
                                + resultIds[0]);
                    }
                }
            }
        }
        if (substance == null) {
            logger.warn("SUBSTANCE | " + grabbingModel.getSubstance() + " | not found in Master Data");
        }
        return substance;
    }

    private Unit fetchUnit() {
        if (this.unitRepository == null) {
            return null;
        }
        List<Unit> units = this.unitRepository.findByNameIgnoreCaseEquals("mg/kg");
        return (units.size() > 0) ? units.get(0) : null;
    }

    private Crop fetchCrop(DKGrabbingModel grabbingModel) {
        if (this.cropRepository == null) {
            return null;
        }
        if (grabbingModel.getCrop() == null) {
            return null;
        }
        Crop crop = this.cropRepository.findByNameIgnoreCaseEquals(grabbingModel.getCrop());
        if (crop == null) {
            List<Crop> result = this.cropRepository.findByOtherNamesIgnoreCaseEquals(grabbingModel.getCrop());
            if (result != null) {
                if (!result.isEmpty()) {
                    crop = result.get(0);
                    if (result.size() > 1) {
                        String[] resultIds = {""};
                        result.forEach(c -> resultIds[0] += c.getId() + " | ");
                        logger.warn("The Crop Name | " + grabbingModel.getCrop()
                                + " | is returning "
                                + result.size()
                                + " results in the search by other names. This is due to same name with different case. Othername IDs: "
                                + resultIds[0]);
                    }
                }
            }
        }
        if (crop == null) {
            logger.warn("CROP | " + grabbingModel.getCrop() + " | not found in Master Data");
        }
        return crop;
    }

    private Country fetchSamplingCountry() {
        if (this.countryRepository == null) {
            return null;
        }
        return this.countryRepository.findByNameIgnoreCaseEquals("Denmark");
    }

    private Country fetchOriginCountry(DKGrabbingModel grabbingModel) {
        if (this.countryRepository == null) {
            return null;
        }
        if (grabbingModel.getCountry() == null) {
            return null;
        }
        Country country = this.countryRepository.findByNameIgnoreCaseEquals(grabbingModel.getCountry());
        if (country == null) {
            List<Country> result = this.countryRepository.findByOtherNamesIgnoreCaseEquals(grabbingModel.getCrop());
            if (result != null) {
                if (!result.isEmpty()) {
                    country = result.get(0);
                    if (result.size() > 1) {
                        String[] resultIds = {""};
                        result.forEach(c -> resultIds[0] += c.getId() + " | ");
                        logger.warn("The Country Name | " + grabbingModel.getCrop()
                                + " | is returning "
                                + result.size()
                                + " results in the search by other names. This is due to same name with different case. Othername IDs: "
                                + resultIds[0]);
                    }
                }
            }
        }

        if (country == null) {
            logger.warn("COUNTRY | " + grabbingModel.getCountry() + " | not found in Master Data");
        }
        return country;
    }
}
