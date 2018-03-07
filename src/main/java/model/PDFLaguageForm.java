/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Language for pdf
 * @author Oscar
 */
class PDFLaguageForm {
    private static final String COMP_NAME = "ReCute";
    private static final String ENGLISH = "en";
    private static final String SWEDISH = "sv";
    
    private static final String NORMAL_DATE_FORMAT = "dd-MM-yyyy";
    private static final String WRONG_DATE_FORMAT = "MM-dd-yyyy";
    
    static final String DEFAULT_LANGUAGE = ENGLISH;
    static final int DEFAULT_LANGUAGE_INDEX = 0;
    static final String[] SUPPORTED_LANGUAGES = {ENGLISH, SWEDISH};
    
    static final String[][] PDF_TITLE = {
        {ENGLISH, "Application"},
        {SWEDISH, "Ansökan"}
    };
    
    static final String[][] PDF_SUBJECT = {
        {ENGLISH, "What is subject?"},
        {SWEDISH, "Vad är subjekt?"}
    };
    
    static final String[][] FORM_MAIN = {
        {ENGLISH, COMP_NAME + " Job Application", "Application information", "Applicant Information", "Competences", "Availability"},
        {SWEDISH, COMP_NAME + " Jobbansökan", "Information Om Ansökan", "Information Om Ansökande", "Kompetenser", "Tillgänglighet"}
    };
    
    static final String[][] FORM_APPLICATION_INFO = {
        {ENGLISH, "Registration Date: ", "Current Status: "},
        {SWEDISH, "Ansökningsdatum: ", "Aktuell Status: "}
    };
    
    static final String[][] FORM_USER_INFO = {
        {ENGLISH, "First name: ", "Surame: ", "Email: ", "Social Security Number: "},
        {SWEDISH, "Förnamn: ", "Efternamn: ", "E-post: ", "Personnummer: "}
    };
    
    static final String[][] FORM_COMPETENCE_HEADERS = {
        {ENGLISH, "Name", "Years Of Experience"},
        {SWEDISH, "Namn", "Antal Erfarenhetsår"}
    };
    
    static final String[][] FORM_AVAILABILITY_HEADERS = {
        {ENGLISH, "From", "To"},
        {SWEDISH, "Från", "Till"}
    };
    
    static final String[][] PDF_EXCEPTION_MSG = {
        {ENGLISH, "Error generationg pdf."},
        {SWEDISH, "Något gick fel vid skapandet av pdf"}
    };

    static final String[][] DATE_FORMAT = {
        {ENGLISH, WRONG_DATE_FORMAT},
        {SWEDISH, NORMAL_DATE_FORMAT}
    };
    
}
