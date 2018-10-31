package com.bkash.frestivalreg.commands;

import java.io.Serializable;

/**
 * Created by alam.ashraful on 7/8/2018.
 */
public class RegistrationDocument implements Serializable {
    private String documentType;
    private String documentPath;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    @Override
    public String toString() {
        return "RegistrationDocument{" +
                "documentType='" + documentType + '\'' +
                ", documentPath='" + documentPath + '\'' +
                '}';
    }
}
