package com.mt_ag.bayer.cmc.core.mapping;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;

import java.io.IOException;
import java.io.InputStream;

class UKMapperTest {

    @Test
    void givenOdsUrlThenTable() {
        WebClientFactory webClientFactory = new WebClientFactory();
        WebClient webClient = webClientFactory.create();
        SpreadsheetDocument doc = null;
        String odsLink = "http://data.defra.gov.uk/PRIF/Q4_2019_quarterly_data.ods";
        Page ods = null;
        try {
            ods = webClient.getPage(odsLink);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream odsInputStream = ods.getWebResponse().getContentAsStream()) {
            doc = SpreadsheetDocument.loadDocument(odsInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        for (Table t : doc.getTableList()) {
//            String tableName = t.getTableName();
//            System.out.println(tableName);
//        }

        Assertions.assertNotNull(doc.getTableList());
        Assertions.assertNotEquals(doc.getTableList().size(), 0);

    }
}