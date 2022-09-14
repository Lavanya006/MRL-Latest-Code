package com.mt_ag.bayer.cmc.constants;

public class SystemConstants {

    public static final String DEV = "DEV";
    public static final String PROD = "PROD";
    public static final String QA = "QA";

    public static final String IS_ON = "ON";
    public static final String IS_OFF = "OFF";

    //General
    public static final String MAIL_TO = "mailto";
    public static final String JAVASCRIPT = "javascript";
    public static final String HREF = "href";
    public static final String HTTP = "http";
    public static final String H1 = "h1";
    public static final String A = "a";

    public static final String MG_KG = "mg/kg";
    public static final String SUM = "(sum)";
    public static final String DATE_FORMAT_STRING = "dd/MM/yyyy";
    public static final String NOT_FOUND_IN_MASTER_DATA = " not found in Master Data";

    public static final int FIRST_ELEMENT = 0;

    public static final double DEFAULT_MRL_VALUE = 0.01;

    //DK Mapper
    public static final String DK_EXTRACT_STRING = "from:";
    public static final String DK_COLLECTED_DATA_STRING = "Collected Data: ";
    public static final String DK_RELEVANT_PAGES_STRING = "relevant Pages: ";
    public static final String DK_DENMARK = "Denmark";
    public static final String DK_HOME = "https://www.food.dtu.dk/";
    public static final String DK_ERROR_MESSAGE_LINKS_PDF = "Could not fetch the PDF Links";
    public static final String DK_ERROR_MESSAGE_PARSE = "Parse error in";
    public static final String DK_WORD_SEPARATOR = "<WORD!>";
    public static final String DK_LINE_SEPARATOR = "<LINE!>";
    public static final String DK_IS_ECOLOGIC = "Is ecological";
    public static final String DK_ECOLOGIC = "(økologisk)";
    public static final String DK_DATE_REGEX = "((([0-9]{2}\\.[0-9]{2}\\.[0-9]{4})|([0-9]{2}-[0-9]{2}-[0-9]{4})|([0-9]{2}\\/[0-9]{2}\\/[0-9]{4})))";
    public static final String DK_CONCLUSION_TILL_DATE_REGEX = "((Resultatet|Fundet)(.*?))" + DK_DATE_REGEX;
    public static final String DK_DOUBLE_REGEX = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
    public static final String DK_TEXT_REGEX = "([a-zA-Z])+";
    public static final String DK_EVERYTHING_REGEX = "(.*)";
    public static final String DK_TABLE_FINDER = "Tabel 2. Prøver med overskridelser";
    public static final String DK_HEADER_REGEX = "Vare(.*?)prøve";
    public static final String DK_HEADER2_REGEX = "Prøver, udtaget på baggrund af mistanke";
    public static final String DK_HEADER3_REGEX = "Prøver, udtaget som repræsentative for markedet \\(stikprøver\\)";
    public static final String DK_HEADER4_REGEX = "DTU Fødevareinstituttet";
    public static final String DK_PAGE_REGEX = "- [0-9]* -";
    public static final String DK_NOTE_REGEX = "\\*|Maksimalgrænseværdier";
    public static final String DK_PUBLICATION_KEYWORD = "Publikationer";
    public static final String[] DK_URL_KEYWORD_FILTER = {"Pesticidkontrol", "Pesticidrester", "foedevarer", "fødevarer", "kvartal"};
    public static final String[] DK_TABLE_SCHEMA_KEYWORDS = {"Vare", "Produceret", "Pesticid", "Analyse", "MRL", "Kontrolkonklusion", "Dato"};

    //RASFF Mapper
    public static final String RASFF_MAIN_CONTENT = "mainContent";
    public static final String RASFF_RESULTS_COUNT_REGEX = "Search result: ([0-9]+) notifications";
    public static final String RASFF_KEYWORD_ID = "/div[/contains(@class,'col-xl-2 col-lg-2 cig-middle')]";
    public static final String RASFF_SEARCH_KEYWORD_CLICK_ID = "Open URL";
    public static final String RASFF_NEXT_PAGE_CLICK_ID = "Next 100";
    public static final String RASFF_RESULT_TABLE_NAME = "Result";
    public static final String RASFF_HAZARD_TABLE_NAME = "hazards";
    public static final String RASFF_SUSPECT_XPATH = "/div[/contains(@class, 'span12')]";
    public static final String RASFF_INFO_FIELDS_XPATH = "//span[contains(@class, 'uneditable-input')]";
    public static final String RASFF_NEXT_PAGE_XPATH = "//li[contains(@class, 'next disabled')]";
    public static final String RASFF_FOLLOW_UP_TABLE_XPATH = "//table[contains(@class, 'table table-hover table-striped')]";
    public static final String RASFF_ORIGIN_COUNTRY_STRING = "(O)";
    public static final String RASFF_XPATH_MAINCONTENT = "//div[contains(@class, 'contentmain')]";
    public static final String RASFF_XPATH_A = "//a";
    public static final String RASFF_SOURCE_SUBSTANCE_NAME_STRING = "Active Ingredient in Source System";
    public static final int RASFF_DETAIL_CLICK_COLUMN_POSITION = 9;

    //UK Mapper
    public static final String UK_UNITED_KINGDOM = "United Kingdom";
    public static final String UK_ERROR_MESSAGE_SPREADSHEET = "COULD NOT INITIALIZE THE SpreadsheetDocument";
    public static final String UK_ERROR_MESSAGE_LINKS_ODS = "Could not fetch the ODS Links";
    public static final String UK_DOWNLOD_ODS_LINK = "//a[@data-ga-event='download']";
    public static final String UK_TABLE_ODS_LINKS = "//table[@class='govuk-table govuk-!-margin-bottom-4']";
    public static final String UK_NONE = "None";
    public static final String UK_DATE_OF_SAMPLING = "Date of Sampling";
    public static final String UK_MRL_REGEX = "MRL = (\\d+\\.?\\d+|\\d+)";
    public static final String UK_DETECTED_REGEX = "(\\d+\\.?\\d+|\\d+)";
    public static final String UK_SUBSTANCE_REGEX = "^[a-zA-Z]+\\b";
    public static final String UK_ODS_ENDING_BNA = "_BNA";
    public static final String UK_ODS_ENDING_SUM = "_SUM";

    /**
     *  !!! DANGER !!!
     *  DO NOT EVER CHANGE THIS!!!
     */
    public static final String NOTIFICATION_TITLE_FOUND_IN = " found in ";
    public static final String NOTIFICATION_TITLE_FROM = " from ";
    public static final String NOTIFICATION_TITLE_IN = " in ";
    public static final String NOTIFICATION_TITLE_BEGIN = "MRL: ";
    public static final String NOTIFICATION_DESCRIPTION_OCCURED_IN = "MRL exceedance occured in: ";
    public static final String NOTIFICATION_DESCRIPTION_IMPORT = "Import from Crop Monitoring Cockpit ";
    public static final String NOTIFICATION_LINE_BREAK = "#LINEBREAK#";
    public static final String RASFF_UNAUTHORISED_SUBSTANCE_STRING = "unauthorised substance";
    public static final String RASFF_PROHIBITED_SUBSTANCE_STRING = "prohibited substance";
    public static final String DATA_SOURCE_NAME = "Data Source: ";
}
