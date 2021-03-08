package com.example.splitreceipt.dto;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
public class ErrorResultDto {

    private String code;
    private String description;

    public ErrorResultDto(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
