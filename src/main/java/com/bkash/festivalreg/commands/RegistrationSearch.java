package com.bkash.festivalreg.commands;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationSearch {

    @NotNull
    @Size(min=11, max=11)
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Only numeric value is required")
    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
