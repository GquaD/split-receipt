package com.example.splitreceipt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */


public class InputCreateDto {
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("receipt_id")
    private Integer receiptId;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("comment")
    private String comment;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
