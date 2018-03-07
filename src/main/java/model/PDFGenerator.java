/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a PDF generator which creates a pdf of applications.
 *
 * @author Oscar
 */
public class PDFGenerator {

    private final ApplicationDetailsDTO appDetails;
    private String language;
    private int languageIndex;
    private final Document document;
    private final Font catFont = new Font(Font.TIMES_ROMAN, 20, Font.BOLD);
    private final Font normalFont = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.GRAY);
    private final Font subFont = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
    private final Font smallBold = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);

    /**
     * Initializes this generator to generate a pdf with the specified
     * application detials in the specified language.
     *
     * @param appDetails details of the application.
     * @param language language to generate pdf in.
     */
    public PDFGenerator(ApplicationDetailsDTO appDetails, String language) {
        this.appDetails = appDetails;
        setLanguageAndIndexOrDefault(language);
        this.document = new Document();
    }

    /**
     * Generates the pdf of the application and returns it.
     *
     * @return byte[] as Object with the content of the pdf.
     */
    public Object generatePDF() {
        try {
            ByteArrayOutputStream pdf = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, pdf);

            document.open();

            addMetaData();
            addApplicationContent();

            document.close();

            return pdf.toByteArray();
        } catch (Exception ex) {
            // Log reason of failure
            //throw new Exception(PDFLaguageForm.PDF_EXCEPTION_MSG[languageIndex][1]);
            throw new AppRuntimeException(ErrorMessageEnum.OPERTAION_FAILED.toString());
        }
    }

    private void addMetaData() throws Exception {
        document.addTitle(PDFLaguageForm.PDF_TITLE[languageIndex][1]);
        document.addSubject(PDFLaguageForm.PDF_SUBJECT[languageIndex][1]);
    }

    private void addApplicationContent() throws Exception {
        int index = 1;

        Paragraph preface = new Paragraph();

        addEmptyLine(preface, 1);
        preface.add(new Paragraph(PDFLaguageForm.FORM_MAIN[languageIndex][index++], catFont));

        addEmptyLine(preface, 1);
        preface.add(new Paragraph(PDFLaguageForm.FORM_MAIN[languageIndex][index++], subFont));
        addApplicationInformation(preface);

        addEmptyLine(preface, 1);
        preface.add(new Paragraph(PDFLaguageForm.FORM_MAIN[languageIndex][index++], subFont));
        addUserInformation(preface);

        addEmptyLine(preface, 1);
        preface.add(new Paragraph(PDFLaguageForm.FORM_MAIN[languageIndex][index++], subFont));
        addCompetences(preface);

        addEmptyLine(preface, 1);
        preface.add(new Paragraph(PDFLaguageForm.FORM_MAIN[languageIndex][index++], subFont));
        addAvailabilities(preface);

        document.add(preface);
    }

    private void addEmptyLine(Paragraph paragraph, int number) throws Exception {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void addApplicationInformation(Paragraph preface) throws Exception {
        int index = 1;

        String statusName = null;
        for (StatusNameDTO status : appDetails.getStatusName()) {
            if (status.getSupportedLanguage().equals(language)) {
                statusName = status.getName();
                break;
            }
        }

        preface.add(getParagraphWithChunk(PDFLaguageForm.FORM_APPLICATION_INFO[languageIndex][index++], smallBold, getSimpleDate(appDetails.getRegistrationDate()), normalFont));
        preface.add(getParagraphWithChunk(PDFLaguageForm.FORM_APPLICATION_INFO[languageIndex][index], smallBold, statusName, normalFont));
    }

    private void addUserInformation(Paragraph preface) throws Exception {
        int index = 1;
        preface.add(getParagraphWithChunk(PDFLaguageForm.FORM_USER_INFO[languageIndex][index++], smallBold, appDetails.getUser_firstname(), normalFont));
        preface.add(getParagraphWithChunk(PDFLaguageForm.FORM_USER_INFO[languageIndex][index++], smallBold, appDetails.getUser_surname(), normalFont));
        preface.add(getParagraphWithChunk(PDFLaguageForm.FORM_USER_INFO[languageIndex][index++], smallBold, appDetails.getUser_email(), normalFont));
        preface.add(getParagraphWithChunk(PDFLaguageForm.FORM_USER_INFO[languageIndex][index++], smallBold, appDetails.getUser_ssn(), normalFont));
    }

    private void addCompetences(Paragraph preface) throws Exception {
        PdfPTable table = getTable(PDFLaguageForm.FORM_COMPETENCE_HEADERS[languageIndex], smallBold, Element.ALIGN_CENTER);

        appDetails.getCompetenceProfiles().forEach(profile -> {
            if (profile.getLanguage().equals(language)) {
                table.addCell(profile.getCompetenceName());
                table.addCell("" + profile.getYearsOfExperience());
            }
        });

        preface.add(table);
    }

    private void addAvailabilities(Paragraph preface) throws Exception {
        PdfPTable table = getTable(PDFLaguageForm.FORM_AVAILABILITY_HEADERS[languageIndex], smallBold, Element.ALIGN_CENTER);

        appDetails.getAvailabilities().forEach(availability -> {
            table.addCell(getSimpleDate(availability.getFromDate()));
            table.addCell(getSimpleDate(availability.getToDate()));
        });

        preface.add(table);
    }

    private Paragraph getParagraphWithChunk(String text1, Font font1, String text2, Font font2) throws Exception {
        Paragraph paragraph = new Paragraph(text1, font1);
        paragraph.add(new Chunk(text2, font2));
        return paragraph;
    }

    private PdfPTable getTable(String[] headers, Font font, int align) throws Exception {
        PdfPTable table = new PdfPTable(headers.length - 1);

        for (int i = 1; i < headers.length; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(headers[i], font));
            cell.setHorizontalAlignment(align);
            table.addCell(cell);
        }

        table.setHeaderRows(1);

        return table;
    }

    private String getSimpleDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PDFLaguageForm.DATE_FORMAT[languageIndex][1]);
        return sdf.format(date);
    }

    private void setLanguageAndIndexOrDefault(String lang) {
        String[] availableLanguages = PDFLaguageForm.SUPPORTED_LANGUAGES;

        for (int i = 0; i < availableLanguages.length; i++) {
            if (availableLanguages[i].equals(lang)) {
                language = lang;
                languageIndex = i;
                return;
            }
        }

        language = PDFLaguageForm.DEFAULT_LANGUAGE;
        languageIndex = PDFLaguageForm.DEFAULT_LANGUAGE_INDEX;
    }

}
