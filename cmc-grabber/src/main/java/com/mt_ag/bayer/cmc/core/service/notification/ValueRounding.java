package com.mt_ag.bayer.cmc.core.service.notification;

import java.math.BigDecimal;

public class ValueRounding {

    public static BigDecimal round(double value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_UP);
    }
}
