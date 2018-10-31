package com.bkash.frestivalreg.commands;

import java.io.Serializable;

/**
 * Created by alam.ashraful on 7/10/2018.
 */
public class PdfReportResponse implements Serializable {
    private String fileName;
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
