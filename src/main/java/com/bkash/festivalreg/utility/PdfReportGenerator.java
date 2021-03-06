package com.bkash.festivalreg.utility;

import com.bkash.festivalreg.controller.WebAppContoller;
import com.bkash.festivalreg.domain.Registration;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class PdfReportGenerator {

  //  @Value("${pdf-assets}")
   // private String PDF_ASSETS;

    @Value("${applicant-image-path}")
    private String APPLICANT_PHOTO_ASSETS;


    private static final Logger LOGGER = LoggerFactory.getLogger(PdfReportGenerator.class);



    public void createCustomerRegistrationPDF(String filename, String path, Registration data) throws DocumentException, IOException {


        //  MerchantKycData kycData = data.getKycData();
        Font f1 = FontFactory.getFont("Times Roman", 8, BaseColor.BLACK);
        Paragraph paragraph;

        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(.5f, .5f, .5f, .5f);

        PdfWriter.getInstance(document, new FileOutputStream(path + "/" + filename));
        document.open();


        ////////////////////////


        PdfPTable header = new PdfPTable(3);
        header.setWidths(new int[]{2, 2, 1});
        header.setSpacingBefore(1);

        Resource resource = new ClassPathResource("/static/images/bkash.png");

        //Image img = Image.getInstance(PDF_ASSETS + "bkash.png");
        Image img = Image.getInstance(resource.getURL());

        // img.scaleToFit(100, 100);
        Chunk chunk = new Chunk(img, 0, 0, true);


        Phrase phraseImg = new Phrase();
        phraseImg.add(chunk);
        Paragraph paraImg = new Paragraph();
        paraImg.add(phraseImg);
        paraImg.setAlignment(Element.ALIGN_CENTER);

        PdfPCell cell = new PdfPCell(phraseImg);
        cell.setColspan(2);
        cell.setPaddingLeft(200);
        cell.setRowspan(1);
        cell.setBorder(PdfPCell.NO_BORDER);
        header.addCell(cell);

        //Right Column

        try {

           // String photopath = APPLICANT_PHOTO_ASSETS + data.getAccountNumber() + ".jpg";
           // img = Image.getInstance("http://shohoz-rides.s3-ap-southeast-1.amazonaws.com/staging/events/81891ce4e82d1292cb8585f41192daa9.jpg");
           // img = Image.getInstance(photopath);

            System.setProperty("http.proxyHost", "10.32.66.252");
            System.setProperty("http.proxyPort", "4951");
            System.setProperty("https.proxyHost", "10.32.66.252");
            System.setProperty("https.proxyPort", "4951");


            LOGGER.warn("FolkFestApp::PdfReportGenerator::photo path is  " + data.getPhotoPath()+" user is :"+getUserName());

            img =Image.getInstance(data.getPhotoPath().trim());
            img.scaleToFit(90, 90);
            chunk = new Chunk(img, 0, 0, true);
            phraseImg = new Phrase();
            phraseImg.add(chunk);
            paraImg = new Paragraph();
            paraImg.add(phraseImg);
            paraImg.setAlignment(Element.ALIGN_CENTER);
            cell = new PdfPCell(phraseImg);

        } catch (Exception ex) {
            // do nothing
           // ex.printStackTrace();
            cell = new PdfPCell(new Phrase("No photo"));

        }


        cell.setColspan(1);
        cell.setRowspan(3);
        cell.setPaddingTop(10);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);

        header.addCell(cell);

        Font fontSize10Bold = FontFactory.getFont("Times Roman", 10, Font.BOLD);
        paragraph = new Paragraph("bKash Customer Account Opening Application", fontSize10Bold);
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED_ALL);

        cell = new PdfPCell(paragraph);
        cell.setPaddingLeft(150);
        cell.setColspan(2);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setRowspan(1);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        header.addCell(cell);

        Font fontSize8Bold = FontFactory.getFont("Times Roman", 8, Font.BOLD);
        paragraph = new Paragraph("(New Registration / Information Update)", fontSize8Bold);
        paragraph.setIndentationLeft(250);

        cell = new PdfPCell(paragraph);
        cell.setColspan(2);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setRowspan(1);
        cell.setPaddingLeft(180);
        header.addCell(cell);


        document.add(header);

        PdfPTable table = new PdfPTable(8);
        table.setWidths(new int[]{1, 1, 1, 1, 1, 1, 1, 1});
        table.setSpacingBefore(3);

        //form serial
        Font fontSize8Black = FontFactory.getFont("Times Roman", 8, BaseColor.BLACK);
        addContent(table, fontSize8Black, 8, "Form Serial # " + data.getFormSerial(), true, 0);
        String reportDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
        addContent(table, fontSize8Black, 8, "Date " + reportDate, true, 0);
        addContent(table, fontSize8Black, 8, "Account Number # " + data.getAccountNumber(), true, 0);
        addContent(table, fontSize8Black, 8, "    ", true, 0);
        // addContent(table, font, 6, ""+data.getFormSerial(), true, 0);

        //end of form serial


        Font subtitleFont = FontFactory.getFont("Times Roman", 7, BaseColor.BLACK);

        Font fontParam = FontFactory.getFont("Times Roman", 10, Font.BOLD);
        PdfPCell cell1;
        cell1 = new PdfPCell(new Phrase("Mandatory Part-1", fontParam));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setColspan(8);
        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell1.setBorder(PdfPCell.NO_BORDER);
        cell1.setPaddingTop(5);
        cell1.setPaddingBottom(5);
        table.addCell(cell1);


        Font font = FontFactory.getFont("Times Roman", 7, BaseColor.BLACK);

        addContent(table, font, 8, "    ", true, 0);

        addContent(table, font, 8, "1.  Applicant’s Name", true, 0);
        //addContent(table, font, 6, data.getAccountFirstName()+" "+data.getAccountLastName(), true, 0);

        addContent(table, font, 2, "First Name", true, 10);
        addContent(table, font, 6, data.getAccountFirstName(), true, 0);

        addContent(table, font, 2, "Last Name:", true, 10);
        addContent(table, font, 6, data.getAccountLastName(), true, 0);

        addContent(table, font, 2, "2.  Date of Birth Date: ", true, 0);
        String formattedDate = "";

        if (data.getAccountBirthDate() != null) {
            formattedDate = new SimpleDateFormat("dd-MMM-yyyy").format(data.getAccountBirthDate());
        }

        addContent(table, font, 6, formattedDate, true, 0);

        addContent(table, font, 2, "3.  Father's Name:", true, 0);
        addContent(table, font, 6, data.getAccountFatherName(), true, 0);

        addContent(table, font, 2, "4.  Mother's Name:", true, 0);
        addContent(table, font, 6, data.getAccountMotherName(), true, 0);

        addContent(table, font, 2, "5.  Husband/Wife's Name:", true, 0);
        addContent(table, font, 6, data.getAccountHasbandWifeName(), true, 0);

        addContent(table, font, 2, "6.  ID Type:", true, 0);
        addContent(table, font, 6, data.getIdType(), true, 0);


        addContent(table, font, 2, "7.  ID Number: ", true, 0);
        addContent(table, font, 6, data.getIdNumber(), true, 0);
        addContent(table, font, 8, "    ", true, 0);


        fontParam = FontFactory.getFont("Times Roman", 10, Font.BOLD);
        cell1 = new PdfPCell(new Phrase("Mandatory Part-2", fontParam));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setColspan(8);
        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell1.setBorder(PdfPCell.NO_BORDER);
        cell1.setPaddingTop(5);
        cell1.setPaddingBottom(5);
        table.addCell(cell1);


        addContent(table, font, 8, "             ", true, 0);

        addContent(table, font, 2, "8.  Present Address: ", true, 0);
        addContent(table, font, 6, data.getPresentAddress(), true, 0);

        addContent(table, font, 2, "9.  Permanent Address: ", true, 0);
        addContent(table, font, 6, data.getPermanentAddress(), true, 0);

        addContent(table, font, 2, "10. Gender: ", true, 0);
        addContent(table, font, 6, data.getGender(), true, 0);

        addContent(table, font, 2, "11. Source of Fund: ", true, 0);
        addContent(table, font, 6, data.getSourceOfFund(), true, 0);

        addContent(table, font, 2, "12. Estimated Monthly Income: ", true, 0);
        DecimalFormat df = new DecimalFormat("#.00");

        if (data.getEstimatedMonthlyIncome() != null){
            addContent(table, font, 6, "" + df.format(data.getEstimatedMonthlyIncome()), true, 0);
        }
        else
        {
            addContent(table, font, 6, "" + 0.00, true, 0);
        }
        addContent(table, font, 2, "13. Detials of Occupation: ", true, 0);
        addContent(table, font, 6,""+ data.getDetailsOfOccupation(), true, 0);

        document.add(table);

        String textT = "Note: According to Money Laundering Prevention Act, 2012, clause 8 (1) No person shall knowingly provide false information in any manner regarding the source of"+
        "fund or self-identity or the identity of an account holder or the beneficiary or nominee of an account. And as per Money Laundering Prevention Act, 2012, clause 8 (2)"+
        "any person who violates the provision of sub-section (1) shall be punished with imprisonment for a term not exceeding 3 (three) years or a fine not exceeding taka 50"+
        "(fifty) thousand or with both."+
               "I hereby confirm that being a user of complete Mobile Financial Services’ of “bKash Limited”, I have read, and agreed to maintain all the terms and conditions as"+
        "stated in the other page. I will follow all the rules and regulations related to opening, maintaining and operating the account. All information and documents given in"+
        "this application form, according to my knowledge, are correct and complete and do not conceal any"+
        "information and I am aware of the provisions of the law;";

        paragraph = new Paragraph(textT, subtitleFont);
        paragraph.setIndentationLeft(60);
        paragraph.setIndentationRight(50);
        paragraph.setSpacingBefore(5);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);

        //End of NomineeTable

        PdfPTable tablebottom = new PdfPTable(3);
        tablebottom.setWidths(new int[]{3, 2, 1});
        tablebottom.setSpacingBefore(15);

        addContent(tablebottom, font, 3, "    ", true, 0);
        //addContent(tablebottom, font, 3, "    ", true, 0);

        addContent(tablebottom, font, 1, "..........................................................", true, 0);
        addContent(tablebottom, font, 1, "   ", true, 0);
        addContent(tablebottom, font, 1, "..................", true, 0);

        addContent(tablebottom, font, 1, "Customer’s Signature/Thumbprint", true, 0);
        addContent(tablebottom, font, 1, "     ", true, 0);
        addContent(tablebottom, font, 1, "Date", true, 0);

        addContent(tablebottom, font, 3, "    ", true, 0);
           addContent(tablebottom, font, 3, " Attachments: 1.Photo ID:(Yes/No)   2.One Copy of Applicant’s Photo:(Yes/No)   3. Others:(Yes/No)", true, 0);
        addContent(tablebottom, font, 3, "    ", true, 0);
        document.add(tablebottom);


        addShaddedHeader("Agent Use Only", document);

        textT = " I hereby, confirmed that, applicant has signed in my presence and provided photo is the applicant’s own. Also, " +
                "the mobile number used to open account was along with the customer while opening the account. ";

        paragraph = new Paragraph(textT, subtitleFont);
        paragraph.setIndentationLeft(60);
        paragraph.setIndentationRight(50);
        paragraph.setSpacingBefore(5);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);

        PdfPTable tablebottom3 = new PdfPTable(3);

        tablebottom3.setWidths(new int[]{3, 2, 1});
        tablebottom3.setSpacingBefore(20);

        addContent(tablebottom3, font, 1, "..........................................................", true, 0);
        addContent(tablebottom3, font, 1, ".......................................", true, 0);
        addContent(tablebottom3, font, 1, "..................", true, 0);

        addContent(tablebottom3, font, 1, "Agent's Name", true, 0);
        addContent(tablebottom3, font, 1, "Signature & Seal:", true, 0);
        addContent(tablebottom3, font, 1, "Date", true, 0);

        addContent(tablebottom3, font, 3, "    ", true, 0);

        document.add(tablebottom3);

        addShaddedHeader("Distributor Use Only", document);

        textT = "I hereby confirm that, information in this KYC Form has been verified and found correct";
        paragraph = new Paragraph(textT, subtitleFont);
        paragraph.setIndentationLeft(60);
        paragraph.setIndentationRight(50);
        paragraph.setSpacingBefore(5);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);

        PdfPTable tablebottom4 = new PdfPTable(3);
        tablebottom4.setWidths(new int[]{3, 2, 1});
        tablebottom4.setSpacingBefore(20);


        addContent(tablebottom4, font, 1, "..........................................................", true, 0);
        addContent(tablebottom4, font, 1, ".......................................", true, 0);
        addContent(tablebottom4, font, 1, "..................", true, 0);

        addContent(tablebottom4, font, 1, "Distributor’s Assigned Person’s Name", true, 0);
        addContent(tablebottom4, font, 1, "Signature & Seal:", true, 0);
        addContent(tablebottom4, font, 1, "Date", true, 0);

        addContent(tablebottom4, font, 3, "    ", true, 0);

        addContent(tablebottom4, font, 3, " N.B bKash Account means the mobile account held with bKash ", true, 0);

        addContent(tablebottom4, font, 3, "    ", true, 0);

        addContent(tablebottom4, font, 3, "Page 1 of 2", true, 0);
        document.add(tablebottom4);

        document.close();
    }


    private String getUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return name;
    }


    private void addShaddedHeader(String text, Document document) throws DocumentException {
        PdfPTable shadedHeaderTable = new PdfPTable(1);
        Font f1 = FontFactory.getFont("Times Roman", 10, Font.BOLD);
        PdfPCell shadedHeaderCell = new PdfPCell(new Phrase(text, f1));
        shadedHeaderCell.setColspan(1);
        shadedHeaderCell.setPaddingLeft(200);
        shadedHeaderCell.setPaddingTop(5);
        shadedHeaderCell.setPaddingBottom(5);
        shadedHeaderCell.setNoWrap(false);
        shadedHeaderCell.setBorder(PdfPCell.NO_BORDER);

        shadedHeaderCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        shadedHeaderTable.addCell(shadedHeaderCell);
        document.add(shadedHeaderTable);
    }


    private String getDocumentType(String type) {
        String documentType = "Others";

        Map<String, String> documentTypeMap = new HashMap<>();

        documentTypeMap.put("NID", "NID");
        documentTypeMap.put("PASSPORT", "Passport");
        documentTypeMap.put("PHOTO", "1 copy passport size photo");
        documentTypeMap.put("TRADE_LICENSE", "Trade license");
        documentTypeMap.put("TIN", "TIN");
        documentTypeMap.put("DRIVING_LICENSE", "Driving License");
        documentTypeMap.put("BIRTH_CERTIFICATE", "Birth Certificate");
        documentTypeMap.put("OWNERSHIP_DEED", "Ownership Deed");
        documentTypeMap.put("OTHERS", "Others");

        try {
            documentType = documentTypeMap.get(type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return documentType;
    }
    private String getPhotoIdType(String photoId) {
        String photoIdType = "Other";

        Map<String, String> photoIdTypeMap = new HashMap<>();
        photoIdTypeMap.put("NID", "National Identifcation");
        photoIdTypeMap.put("Passport", "Passport");
        photoIdTypeMap.put("Driving-Lisence", "Driving Lisence");
        photoIdTypeMap.put("Birth-Certificate", "Birth Certificate");
        photoIdTypeMap.put("Other", "Other");

        try {
            photoIdType = photoIdTypeMap.get(photoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photoIdType;
    }

/*
    public void createReportPageTnC(String filename, String path, MerchantData data) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path + "/" + filename));
        document.open();

        Paragraph p = new Paragraph(data.getAccountData().getAccountNumber());
        p.setLeading(12);
        p.setIndentationLeft(110);

        document.add(p);

        PdfContentByte canvas = writer.getDirectContentUnder();
        Image image = Image.getInstance(PDF_ASSETS + "/T&C.jpg");
        image.scaleAbsolute(PageSize.A4);
        image.setAbsolutePosition(0, 0);
        canvas.addImage(image);

        document.close();
    }
*/
    private String getFormattedDate(Date date) {

        if (date != null) {
            return new SimpleDateFormat("dd-MMM-yyyy").format(date);
        } else {
            return "na";
        }
    }

    private void addContent(PdfPTable table, Font font, int colspan, String text, boolean isLabel, int leftPadding) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setColspan(colspan);
        cell.setPaddingLeft(leftPadding);
        cell.setNoWrap(false);

        if (!isLabel) {
            cell.setCellEvent(new DottedCell(PdfPCell.BOTTOM));
        }
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
    }

    private void addContentWithBorderStyle(PdfPTable table, Font font, int colspan, String text, boolean isLabel, int leftPadding) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setColspan(colspan);
        cell.setPaddingLeft(leftPadding);
        cell.setNoWrap(false);
        table.addCell(cell);
    }


    private void addContentWithTable(PdfPTable table, int colspan, PdfPTable innertable) {

        PdfPCell cell = new PdfPCell(innertable);
        cell.setColspan(colspan);
        cell.setPaddingLeft(5);
        //  cell.setPaddingLeft(leftPadding);

        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
    }


    private void addCellToCustomtable(PdfPTable table, Font font, String text, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setColspan(colspan);
        table.addCell(cell);
    }




}
