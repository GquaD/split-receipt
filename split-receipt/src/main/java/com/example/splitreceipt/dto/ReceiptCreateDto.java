package com.example.splitreceipt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Behzod on 08, March, 2021
 */
public class ReceiptCreateDto {
    @JsonProperty("comment")
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
