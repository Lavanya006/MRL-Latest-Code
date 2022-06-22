package com.mt_ag.bayer.cmc.controller;

import java.time.LocalDateTime;

import com.mt_ag.bayer.cmc.persistence.entity.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mt_ag.bayer.cmc.core.schedule.DKScheduler;
import com.mt_ag.bayer.cmc.core.schedule.RASFFScheduler;
import com.mt_ag.bayer.cmc.core.schedule.UKScheduler;

@RestController
public class GrabberController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GrabberController.class);

	@Autowired
	private RASFFScheduler rasffScheduler;
	private UKScheduler ukScheduler;
	private DKScheduler dkScheduler;

	@Autowired
	public GrabberController(RASFFScheduler rasffScheduler, UKScheduler ukScheduler, DKScheduler dkScheduler) {
		super();
		this.rasffScheduler = rasffScheduler;
		this.ukScheduler = ukScheduler;
		this.dkScheduler = dkScheduler;
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/grab/rasff")
	public void startGrabbingRASFF() {
		rasffScheduler.starManualExecution();
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/grab/uk")
	public void startGrabbingUK() {
		ukScheduler.starManualExecution();
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/grab/dk")
	public void startGrabbingDK() {
		dkScheduler.starManualExecution();
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/grab")
	public void startGrabbingDatasource(@RequestBody DataSource datasource) {

		if (datasource.getName().equals("RASFF")) {
			try {
				LOGGER.info("Grabbing RASFF manually per given datasource by user: " + LocalDateTime.now());
				rasffScheduler.starManualExecution();
			} catch (Exception e) {
				LOGGER.error("Grabbing RASFF manually failed", e);
			}
		} else if (datasource.getName().equals("UK")) {
			try {
				LOGGER.info("Grabbing UK manually per given datasource by user: " + LocalDateTime.now());
				ukScheduler.starManualExecution();
			} catch (Exception e) {
				LOGGER.error("Grabbing UK manually failed", e);
			}
		} else if (datasource.getName().equals("DK")) {
			try {
				LOGGER.info("Grabbing DK manually per given datasource by user: " + LocalDateTime.now());
				dkScheduler.starManualExecution();
			} catch (Exception e) {
				LOGGER.error("Grabbing DK manually failed", e);
			}
		} else {
			LOGGER.error("Grabbing datasource failed: unknown or wrong named datasource");
		}
	}
}

