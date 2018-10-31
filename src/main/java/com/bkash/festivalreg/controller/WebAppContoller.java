package com.bkash.festivalreg.controller;

import com.bkash.festivalreg.commands.PdfReportResponse;
import com.bkash.festivalreg.commands.RegistrationForm;

import com.bkash.festivalreg.commands.RegistrationSearch;
import com.bkash.festivalreg.converter.RegistrationDataToRegistrationForm;
import com.bkash.festivalreg.converter.RegistrationFormToRegistrationData;
import com.bkash.festivalreg.domain.Registration;
import com.bkash.festivalreg.service.RegistrationService;
import com.bkash.festivalreg.utility.FileUtility;
import com.bkash.festivalreg.utility.PdfReportGenerator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import org.springframework.core.io.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class WebAppContoller {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebAppContoller.class);

    private String appMode;
    @Autowired
    private RegistrationFormToRegistrationData registrationFormToRegistrationData;
    @Autowired
    private RegistrationDataToRegistrationForm registrationDataToRegistrationForm;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private PdfReportGenerator generator;
    @Autowired
    private FileUtility fileUtility;
    @Value("${applicant-image-path}")
    private String APPLICANT_IMAGE_PATH;
    @Value("${applicant-nominee-image-path}")
    private String APPLICANT_NOMINEE_IMAGE_PATH;
    @Value("${applicant-kyc-pdf}")
    private String APPLICANT_KYC_PDF;

    @Autowired
    public WebAppContoller(Environment environment) {
        appMode = environment.getProperty("app-mode");
    }

    @RequestMapping("/")
    public String index(Model model) {

        LOGGER.info("FolkFestApp::WebAppContoller::index initiated by " + getUserName());

        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "bKash");
        model.addAttribute("mode", appMode);


        return "index";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String Registration(Model model) {


        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setDataSaved(false);

        LOGGER.info("FolkFestApp::WebAppContoller::Registration initiated by " + getUserName());
        model.addAttribute("registrationSearch", new RegistrationSearch());
        model.addAttribute("registration",registrationForm);
        model.addAttribute("accountNumber", "");
        model.addAttribute("showDownloadLink", false);
        model.addAttribute("isError", false);
        model.addAttribute("isSuccess", false);
        model.addAttribute("showSaveButton", false);

        return "registration/registration";
    }

    private String getUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return name;
    }


    @RequestMapping(value = "/searchRegistration", method = RequestMethod.POST)
    public String searchRegistration(@Valid @ModelAttribute("registrationSearch") RegistrationSearch searchForm, BindingResult bindingResult, Model model, HttpSession session) throws IOException {

        model.addAttribute("showDownloadLink", false);

        LOGGER.info("FolkFestApp::WebAppContoller::searchRegistration initiated by " + getUserName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationSearch", searchForm);
            model.addAttribute("registration", new RegistrationForm());
            model.addAttribute("accountNumber", "");
            return "registration/registration";
        }


        if (searchForm.getSearchKey().trim() == null || searchForm.getSearchKey().trim().equalsIgnoreCase("")) {
            LOGGER.warn("FolkFestApp::WebAppContoller::searchRegistration with empty key initiated by " + getUserName());
            return "redirect:/registration";
        }

        Registration registration = registrationService.getRegistrationByAccountNumber(searchForm.getSearchKey());
        //TODO:data not found message
        //TODO: save button disabled

        RegistrationForm form=null;

        if(registration==null)
        {
            LOGGER.info("FolkFestApp::WebAppContoller::searchRegistration data not found for, account number: " + searchForm.getSearchKey());
            form=new RegistrationForm();
            model.addAttribute("isError", true);
            model.addAttribute("errorMessage", "Data not found!");

        }
        else
        {
            LOGGER.info("FolkFestApp::WebAppContoller::searchRegistration accessed registration data, account number: " + registration.getAccountNumber());
            form= registrationDataToRegistrationForm.convert(registration);
            model.addAttribute("isSuccess", true);

            LOGGER.info("FolkFestApp::WebAppContoller::searchRegistration accessed registration data, account number: " + registration.getAccountNumber()+" Data saved: "+registration.getDataSaved());

            if(registration.getDataSaved()==null) // editable
            {
                model.addAttribute("showSaveButton", true);
                model.addAttribute("successMessage", "Search data found! You can edit");

            }
            else if(registration.getDataSaved()) { //  saved earlier, so will not editable
                model.addAttribute("showSaveButton",  false);
                model.addAttribute("successMessage", "Search data found! Data was edited earlier, You are viewing this data as read only");
                model.addAttribute("showDownloadLink", true);
                model.addAttribute("accountNumber", registration.getAccountNumber());
            }
            else { // not saved earlier, so it is editable
                model.addAttribute("showSaveButton",  true);
                model.addAttribute("successMessage", "Search data found! You can edit");
            }
        }

        model.addAttribute("registrationSearch", searchForm);
        model.addAttribute("registration", form);

        return "registration/registration";
    }


    @RequestMapping(value = "/saveRegistration", method = RequestMethod.POST)
    public String saveRegistration(@ModelAttribute("registration") RegistrationForm form, BindingResult bindingResult, Model model, HttpSession session) throws IOException {

        LOGGER.info("FolkFestApp::WebAppContoller::saveRegistration initiated by " + getUserName());

        Registration registration = registrationFormToRegistrationData.convert(form);
        registration.setAutditUser(getUserName());
        registration.setAuditDate(new Date());//testt
        registration.setDataSaved(true);

        try {
            registrationService.saveRegistration(registration);
            LOGGER.info("FolkFestApp::WebAppContoller::saveRegistration:: Data saved Successfully for account number : " + registration.getAccountNumber());
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMessage", "Data was saved successfully!");


            Registration registrationAfterIncert = registrationService.getRegistrationByAccountNumber(form.getAccountNumber());
            RegistrationForm formdata = registrationDataToRegistrationForm.convert(registrationAfterIncert);
            model.addAttribute("registrationSearch", new RegistrationSearch());
            model.addAttribute("accountNumber", registration.getAccountNumber());
            model.addAttribute("showDownloadLink", true);
            model.addAttribute("registration", formdata);

        } catch (Exception ex) {
            LOGGER.info("FolkFestApp::WebAppContoller::saveRegistration:: Data saved Failed for account number : " + registration.getAccountNumber());
            model.addAttribute("isError", true);
            model.addAttribute("errorMessage", "Data was not successfully saved!");
            model.addAttribute("registrationSearch", new RegistrationSearch());
            model.addAttribute("showDownloadLink", false);
            model.addAttribute("registration", form);
            ex.printStackTrace();
        }


        return "registration/registration";

    }


    @RequestMapping(value = "/printreport", params = {"accountnumber"}, method = RequestMethod.GET)
    public void printReport(@RequestParam(value = "accountnumber") String accountnumber, HttpServletResponse response) throws IOException {

        LOGGER.info("FolkFestApp::WebAppContoller::printReport initiated by " + getUserName());

        if (accountnumber.equalsIgnoreCase("na")) {
            LOGGER.info("FolkFestApp::WebAppContoller::printReport::downloading part 2 ");
            try {
                Resource resource = new ClassPathResource("bKashPortalforFolkFestPart2.pdf");
                InputStream resourceInputStream = resource.getInputStream();

                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment; filename=" + resource.getFilename());
                IOUtils.copy(resourceInputStream, response.getOutputStream());
                response.flushBuffer();
                resourceInputStream.close();

            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }

        } else {

            LOGGER.info("FolkFestApp::WebAppContoller::printReport::downloading part 1 for account number " + accountnumber);

            Registration registration = registrationService.getRegistrationByAccountNumber(accountnumber);

            LOGGER.info("FolkFestApp::WebAppContoller::printReport:: Data retrived for account number " + registration.getAccountNumber());

            List<PdfReportResponse> reports = getFinalReports(registration);

            try {

                String fileName = reports.get(0).getFileName();
                String filePathToBeServed = reports.get(0).getFilePath();
                File fileToDownload = new File(filePathToBeServed + fileName);

                InputStream inputStream = new FileInputStream(fileToDownload);
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
                inputStream.close();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private List<PdfReportResponse> getFinalReports(Registration registration) {

        List<PdfReportResponse> reports = new ArrayList<>();
        try {

            PdfReportResponse pdfReportResponse = new PdfReportResponse();
            String kycPath = APPLICANT_KYC_PDF + registration.getAccountNumber();
            String kycFileName = registration.getAccountNumber() + "_Registration_Part1.pdf";
            fileUtility.makeDirectory(kycPath);
            generator.createCustomerRegistrationPDF(kycFileName, kycPath, registration);
            pdfReportResponse.setFileName(kycFileName);
            pdfReportResponse.setFilePath(kycPath + "/");
            reports.add(pdfReportResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }


    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        // The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

}