package com.bkash.frestivalreg.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alam.ashraful on 7/2/2018.
 */
@Component
public class FileUtility {
    private static Logger LOGGER = LoggerFactory.getLogger(FileUtility.class);


    @Value("${applicant-image-path}")
    private String APPLICANT_IMAGE_PATH;

    @Value("${applicant-account-doccuments}")
    private String APPLICANT_ACCOUNT_DOC_PATH;

    @Value("${applicant-kyc-pdf}")
    private String APPLICANT_KYC_PDF;

    public String dropzoneUpload(String basePath,String id, MultipartHttpServletRequest request){
        String savedPath = null;
        String[] pathVariables = id.split("-");
        try {
            Iterator<String> itr = request.getFileNames();
            while (itr.hasNext()) {
                String uploadedApplicantImageFile = itr.next();
                MultipartFile file = request.getFile(uploadedApplicantImageFile);
                String filename = file.getOriginalFilename();
                filename = pathVariables[0] + "_" + new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date()) + filename.substring(filename.lastIndexOf("."), filename.length());

                String directoryName = basePath + pathVariables[1] + "/" + pathVariables[0];
                makeDirectory(directoryName);
                try {
                    byte[] bytes = file.getBytes();
                    savedPath = saveFile(bytes, directoryName, filename);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedPath;
    }

    public String saveImages(String basePath,String merchantId,String uploadedBy, MultipartFile file) {
        LOGGER.info("FileUtility:: saveApplicantImage:: ");
        String directoryName = basePath+"/"+uploadedBy+"/"+merchantId;
        makeDirectory(directoryName);
        String filename = "";
        String savedPath = "";
            try {
                byte[] bytes = file.getBytes();
                filename = file.getOriginalFilename();
                LOGGER.info("FileUtility:: saveApplicantImage:: filename:: "+filename);
                filename = merchantId + "_" + new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date()) + filename.substring(filename.lastIndexOf("."), filename.length());
                savedPath = saveFile(bytes, directoryName, filename);
            } catch (Exception e) {
                e.printStackTrace();
            }

        return savedPath;
    }
    public List<String> saveUploadedDocuments(String fileType, String merchantId,String uploadedBy, MultipartFile[] files) {
        LOGGER.info("FileUtility:: saveUploadedFiles:: ");
        List<String> pathList = new ArrayList<>();
        String directoryName = APPLICANT_ACCOUNT_DOC_PATH+"/"+uploadedBy+"/"+merchantId+"/"+fileType;
        makeDirectory(directoryName);
        String filename = "";
        String savedPath = "";

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                LOGGER.info("FileUtility:: saveUploadedFiles:: empty file");
                continue; //next pls
            }
            try {
                byte[] bytes = file.getBytes();

                filename = file.getOriginalFilename();
                LOGGER.info("FileUtility:: saveUploadedFiles:: filename:: "+filename);
                filename = merchantId + "_" + new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date()) + filename.substring(filename.lastIndexOf("."), filename.length());
                savedPath = saveFile(bytes, directoryName, filename);

                pathList.add(savedPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return pathList;
    }

    public String saveFile(byte[] bytes,String directoryName, String filename){
        String savedPath = null;
        try {
            LOGGER.info("filePath: "+directoryName+"/"+filename);
            Path path = Paths.get(directoryName + "/" +filename);
            savedPath = String.valueOf(path);
            Files.write(path, bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return savedPath;
    }

    public void makeDirectory(String directoryName){
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
            LOGGER.info("FileUtility directory created!! \n"+directory);
        }
    }

    public void downloadReport(String fileName, String filePath, HttpServletResponse response) {
//        fileName.split(".pdf|.jpg|.JPEG|.png|.PNG")[0]
        try {
            OutputStream os = response.getOutputStream();
            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + ".pdf" + "\"");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

//            FileInputStream inputStream = new FileInputStream(APPLICANT_KYC_PDF +"/"+ fileName + ".pdf");
            FileInputStream inputStream = new FileInputStream(filePath);
            int read = 0;
            byte[] bytes = new byte[4096];

            while ((read = inputStream.read(bytes)) > -1) {
                os.write(bytes, 0, read);
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
