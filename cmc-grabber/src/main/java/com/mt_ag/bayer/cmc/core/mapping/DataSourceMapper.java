package com.mt_ag.bayer.cmc.core.mapping;

import java.io.InputStream;
import java.util.Set;

import com.mt_ag.bayer.cmc.persistence.entity.GrabbingJob;
import com.mt_ag.bayer.cmc.persistence.entity.Measurement;
import com.mt_ag.bayer.cmc.persistence.entity.Substance;

@FunctionalInterface
public interface DataSourceMapper<T> {
    /**
     * Assuming that: 1 - the job parameter has been just saved to the database;
     * ************** 2 - There is at least one job saved before this one;
     * ************** 3 - No one has touched the Measurement on the database;
     * ************** 4 - The last job has run to the completion;
     */
    Set<Measurement> map(GrabbingJob job, Substance substance, InputStream dataSourceInputStream, String dataSourceInfo);
}
