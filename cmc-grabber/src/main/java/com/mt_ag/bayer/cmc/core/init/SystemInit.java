package com.mt_ag.bayer.cmc.core.init;


import javax.annotation.PostConstruct;

import com.mt_ag.bayer.cmc.constants.SystemConstants;
import com.mt_ag.bayer.cmc.core.schedule.DKScheduler;
import com.mt_ag.bayer.cmc.core.schedule.RASFFScheduler;
import com.mt_ag.bayer.cmc.core.schedule.UKScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemInit {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemInit.class);

    private UserMasterData userMasterData;
    private CropMasterData cropMasterData;
    private SubstanceMasterData substanceMasterData;
    private DataSourceMasterData dataSourceMasterData;
    private CountryMasterData countryMasterData;
    private UnitMasterData unitMasterData;
    private MrlMasterData mrlMasterData;
    private RASFFScheduler rasffScheduler;
    private UKScheduler ukScheduler;
    private DKScheduler dkScheduler;
    @Value("${bayer.grabber.dk}")
    private String dkGrabberStatus;
    @Value("${bayer.grabber.uk}")
    private String ukGrabberStatus;
    @Value("${bayer.grabber.rasff}")
    private String rasffGrabberStatus;

    @Autowired
    public SystemInit(UserMasterData userMasterData, CropMasterData cropMasterData,
                      SubstanceMasterData substanceMasterData, DataSourceMasterData dataSourceMasterData,
                      CountryMasterData countryMasterData, RASFFScheduler rasffScheduler, UnitMasterData unitMasterData, MrlMasterData mrlMasterData,
                      UKScheduler ukScheduler, DKScheduler dkScheduler) {
        super();
        this.userMasterData = userMasterData;
        this.cropMasterData = cropMasterData;
        this.substanceMasterData = substanceMasterData;
        this.dataSourceMasterData = dataSourceMasterData;
        this.countryMasterData = countryMasterData;
        this.rasffScheduler = rasffScheduler;
        this.unitMasterData = unitMasterData;
        this.mrlMasterData = mrlMasterData;
        this.ukScheduler = ukScheduler;
        this.dkScheduler = dkScheduler;
    }

    @PostConstruct
    public void init() throws Exception {

        if (this.userMasterData.shouldInit()) {
            this.userMasterData.init();
        } else {
            LOGGER.info("No need to initialize Users");
        }

        if (this.countryMasterData.shouldInit()) {
            this.countryMasterData.init();
        } else {
            LOGGER.info("No need to initialize Countries");
        }

        if (this.dataSourceMasterData.shouldInit()) {
            this.dataSourceMasterData.init();
        } else {
            LOGGER.info("No need to initialize Data Sources");
        }

        if (this.substanceMasterData.shouldInit()) {
            this.substanceMasterData.init();
        } else {
            LOGGER.info("No need to initialize Substances");
        }

        if (this.cropMasterData.shouldInit()) {
            this.cropMasterData.init();
        } else {
            LOGGER.info("No need to initialize Crops");
        }

        if (this.unitMasterData.shouldInit()) {
            this.unitMasterData.init();
        } else {
            LOGGER.info("No need to initialize Units");
        }

        if (this.mrlMasterData.shouldInit()) {
            this.mrlMasterData.init();
        } else {
            LOGGER.info("No need to initialize Mrls");
        }

        // You should probably not change the order these Grabbers are called

        if (this.ukGrabberStatus.equals(SystemConstants.IS_ON)) {
            this.ukScheduler.init();
        } else {
            LOGGER.info("UK Grabber is OFF");
        }

        if (this.dkGrabberStatus.equals(SystemConstants.IS_ON)) {
            this.dkScheduler.init();
        } else {
            LOGGER.info("DK Grabber is OFF");
        }

        if (this.rasffGrabberStatus.equals(SystemConstants.IS_ON)) {
            this.rasffScheduler.init();
        } else {
            LOGGER.info("RASFF Grabber is OFF");
        }

    }

}