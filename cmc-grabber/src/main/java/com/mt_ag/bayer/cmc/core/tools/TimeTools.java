package com.mt_ag.bayer.cmc.core.tools;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class TimeTools {
    public static String calculateDuration(Temporal start, Temporal end) {
        if (ChronoUnit.MILLIS.between(start, end) < 1000) {
            return ChronoUnit.MILLIS.between(start, end) + " milliseconds";
        } else if (ChronoUnit.SECONDS.between(start, end) < 60) {
            return ChronoUnit.SECONDS.between(start, end) + " seconds";
        } else if (ChronoUnit.MINUTES.between(start, end) < 60) {
            return ChronoUnit.MINUTES.between(start, end) + " minutes";
        } else {
            return ChronoUnit.HOURS.between(start, end) + " hours";
        }
    }
}

