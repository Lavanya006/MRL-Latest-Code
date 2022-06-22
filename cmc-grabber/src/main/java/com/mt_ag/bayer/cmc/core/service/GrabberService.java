package com.mt_ag.bayer.cmc.core.service;

import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;

@FunctionalInterface
public interface GrabberService {
	void grabData(GrabbingJob job);

}