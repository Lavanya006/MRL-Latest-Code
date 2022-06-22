package com.mt_ag.bayer.cmc.core.service.notification;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ValueRoundingTest {

    @Test
    void givenStringThenTrheeDecimalPlaces(){
        BigDecimal rounded = ValueRounding.round(0.0345689, 3);
        System.out.println("Rounded: " + rounded);
        Assertions.assertEquals(BigDecimal.valueOf(0.035),rounded);
        Assertions.assertEquals("0.035", rounded.toPlainString());
    }

}