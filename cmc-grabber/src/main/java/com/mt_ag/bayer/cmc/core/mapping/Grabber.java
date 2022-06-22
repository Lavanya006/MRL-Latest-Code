package com.mt_ag.bayer.cmc.core.mapping;

import java.util.Set;

import com.mt_ag.bayer.cmc.persistence.entity.DataSource;
import com.mt_ag.bayer.cmc.persistence.entity.Substance;

@FunctionalInterface
public interface Grabber {	
	Set<?> grab(DataSource dataSource, Substance substance);
}