package com.mt_ag.bayer.cmc.core.service.notification;

import com.mt_ag.bayer.cmc.persistence.repository.AssignedToCairRepository;
import com.mt_ag.bayer.cmc.persistence.repository.MeasurementRepository;
import com.mt_ag.bayer.cmc.constants.SystemConstants;
import com.mt_ag.bayer.cmc.core.service.notification.generated.dev.ImportServiceStub;
import com.mt_ag.bayer.cmc.persistence.entity.AssignedToCair;
import com.mt_ag.bayer.cmc.persistence.entity.Country;
import com.mt_ag.bayer.cmc.persistence.entity.Measurement;
import org.apache.axis2.databinding.types.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class DevNotificationService implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevNotificationService.class);

    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private AssignedToCairRepository assignedToCairRepository;

    @Override
    public NotificationResult notifyCairNew(Measurement measurement) {
        try {
            ImportServiceStub stub = new ImportServiceStub();
            ImportServiceStub.CreateAndUpdateCaseRecordRequest request = new ImportServiceStub.CreateAndUpdateCaseRecordRequest();
            ImportServiceStub.ImportCaseType importCase = new ImportServiceStub.ImportCaseType();
            ImportServiceStub.ImportCaseTypeSequence sequence = new ImportServiceStub.ImportCaseTypeSequence();

            // <AssignedTo>
            AssignedToCair assignedToCair = this.findAssignedToCairByCountry(measurement.getOriginCountry());
            if (assignedToCair != null) {
                ImportServiceStub.CaseAddressListType assignedTo = new ImportServiceStub.CaseAddressListType();
                assignedTo.setName(new Token(assignedToCair.getName()));
                assignedTo.setEmail(new Token(assignedToCair.getEmail()));
                sequence.setAssignedTo(assignedTo);
            }

            // <TeamMembers>
            ImportServiceStub.CaseAddressListType teamMembers[] = new ImportServiceStub.CaseAddressListType[1];
            ImportServiceStub.CaseAddressListType teamMember = new ImportServiceStub.CaseAddressListType();

            teamMember.setName(new Token(MARGOT_NAME));
            teamMember.setEmail(new Token(MARGOT_EMAIL));
            teamMembers[0] = teamMember;
            sequence.setTeamMembers(teamMembers);

            // <PersonToContact>
            ImportServiceStub.CaseAddressListType personToContact = new ImportServiceStub.CaseAddressListType();
            personToContact.setName(new Token(MARGOT_NAME));
            personToContact.setEmail(new Token(MARGOT_EMAIL));
            sequence.setPersonToContact(personToContact);

            // <Title>
            String title = SystemConstants.NOTIFICATION_TITLE_BEGIN + measurement.getSubstance().getName() + SystemConstants.NOTIFICATION_TITLE_IN + measurement.getCrop().getName()
                    + SystemConstants.NOTIFICATION_TITLE_FROM + measurement.getOriginCountry().getName() + SystemConstants.NOTIFICATION_TITLE_FOUND_IN
                    + measurement.getSamplingCountry().getName();
            sequence.setTitle(title);

            // <LeadProblem>
            Token leadProblem = new Token(LEAD_PROBLEM);
            sequence.setLeadProblem(leadProblem);

            // <AdditionalProblem>
            Token additionalProblem = new Token(NONE);
            sequence.setAdditionalProblem(additionalProblem);

            // <SeverityEstimation>
            Token severityEstimation = new Token("Moderate");
            sequence.setSeverityEstimation(severityEstimation);

            // <CountryLeadProduct>
            Token countryLeadProduct = new Token(measurement.getOriginCountry().getName());
            sequence.setCountryLeadProduct(countryLeadProduct);

            // <CaseStatus>
            String statusValue = "Closed - Done";
            if (measurement.getSamplingDate().isAfter(
                    LocalDateTime.of(2019, 12, 31, 23, 59))) {
                statusValue = "Assessment in Progress";
            }
            Token caseStatus = new Token(statusValue);
            sequence.setCaseStatus(caseStatus);


            // <LeadProduct>
            Token leadProduct = new Token(LEAD_PRODUCT);
            sequence.setLeadProduct(leadProduct);

            // <BusinessSegments>
            Token[] businessSegments = {new Token(BUSINESS_SEGMENTS)};
            sequence.setBusinessSegments(businessSegments);

            // <ActiveIngredients>
            Token[] activeIngredients = {new Token(measurement.getSubstance().getName())};
            sequence.setActiveIngredients(activeIngredients);

            // <ExternalSourceSystem>
            String externalSourceSystem = CROP_MONITORING_COCKPIT;
            sequence.setExternalSourceSystem(externalSourceSystem);

            // <OtherIdentifiedNumbers>
            String otherIdentifiedNumbers = "" + measurement.getId();
            sequence.setOtherIdentifiedNumbers(otherIdentifiedNumbers);

            // <DetailedDescription>
            String[] optionalData = {""};
            if (measurement.getOptionalData() != null) {
                if (!measurement.getOptionalData().isEmpty()) {
                    measurement.getOptionalData().forEach((key, value) -> {
                        optionalData[0] += key + ": " + value + SystemConstants.NOTIFICATION_LINE_BREAK;
                    });
                }
            }

            String detailedDescription =
                    SystemConstants.NOTIFICATION_DESCRIPTION_IMPORT //"Import from Crop Monitoring Cockpit "
                            + SystemConstants.NOTIFICATION_LINE_BREAK //"#LINEBREAK#"
                            + SystemConstants.DATA_SOURCE_NAME //Data Source:
                            + measurement.getGrabbingJob().getDataSource().getName()
                            + SystemConstants.NOTIFICATION_LINE_BREAK //"#LINEBREAK#"
                            + SystemConstants.NOTIFICATION_DESCRIPTION_OCCURED_IN // "MRL exceedance occured in: "
                            + measurement.getSamplingCountry().getName()
                            + SystemConstants.NOTIFICATION_LINE_BREAK//"#LINEBREAK#"
                            + optionalData[0];

            sequence.setDetailedDescription(detailedDescription);

            // <CountryCaseHappened>
            Token countryCaseHappened = new Token(measurement.getSamplingCountry().getName());
            sequence.setCountryCaseHappened(countryCaseHappened);

            // <TreatedCropSystem>
            Token treatedCropSystem = new Token(measurement.getCrop().getName());
            sequence.setTreatedCropSystem(treatedCropSystem);

            // <AnalyticalInformation>
            ImportServiceStub.AnalyticalInformation_type0 analyticalInformation = new ImportServiceStub.AnalyticalInformation_type0();
            // <SamplingDate>
            analyticalInformation.setSamplingDate(localDateToDate(measurement.getSamplingDate().toLocalDate()));
            // <MaterialAnalyzed>
            analyticalInformation.setMaterialAnalyzed(new Token(measurement.getCrop().getName()));
            // <LevelOfResidueFound>
            analyticalInformation.setLevelOfResidueFound(ValueRounding.round(measurement.getResidueLevel(), 3));
            // <UnitOfMeasureResidue>
            analyticalInformation.setUnitOfMeasureResidue(new Token(measurement.getUnit().getName()));
            // <SubstanceAnalyzed>
            analyticalInformation.setSubstanceAnalyzed(measurement.getSubstance().getName());
            // <ToleranceLevels>
            analyticalInformation.setToleranceLevels(ValueRounding.round(measurement.getMrl(), 3));

            ImportServiceStub.AnalyticalInformation_type0[] analyticalInformations = {analyticalInformation};
            sequence.setAnalyticalInformation(analyticalInformations);

            // <DateFirstReportedToBayer>
            Date dateFistReportedToBayer = new Date();
            sequence.setDateFirstReportedToBayer(dateFistReportedToBayer);

            // <DateOfOccurrence>
            Date dateOfOccurrence = localDateToDate(measurement.getSamplingDate().toLocalDate());
            sequence.setDateOfOccurrence(dateOfOccurrence);

            importCase.addImportCaseTypeSequence(sequence);
            request.setCreateAndUpdateCaseRecordRequest(importCase);

            LOGGER.info(request.toString());

            ImportServiceStub.CreateAndUpdateCaseRecordResponse response = stub.createAndUpdateCaseRecord(request);
            BigInteger responseStatus = response.getCreateAndUpdateCaseRecordResponse().getResponseStatus();

            if (responseStatus == BigInteger.TWO) {
                String[] errorMessages = response.getCreateAndUpdateCaseRecordResponse().getErrMessages();
                for (String m : errorMessages) {
                    LOGGER.error("ERROR ", m);
                }
                return NotificationResult.REQUEST_FAILED;
            } else if (responseStatus == BigInteger.ONE) {
                String caseCreatedID = response.getCreateAndUpdateCaseRecordResponse().getCaseCreatedID();
                if (measurement.getOptionalData() == null) {
                    measurement.setOptionalData(new HashMap<String, String>());
                    measurement.getOptionalData().put(Measurement.ID_IN_CAIR_NEW, caseCreatedID);
                } else {
                    measurement.getOptionalData().put(Measurement.ID_IN_CAIR_NEW, caseCreatedID);
                }
                updateMeasurement(measurement);
                return NotificationResult.SUCCESS;
            }
        } catch (Exception e) {
            LOGGER.error("ERROR ", e);
            return NotificationResult.HTTP_ERROR;
        }
        return NotificationResult.SUCCESS;
    }

    private void updateMeasurement(Measurement measurement) {
        this.measurementRepository.save(measurement);
    }

    private Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private AssignedToCair findAssignedToCairByCountry(Country country) {
        return assignedToCairRepository.findByCountry(country);
    }


}
