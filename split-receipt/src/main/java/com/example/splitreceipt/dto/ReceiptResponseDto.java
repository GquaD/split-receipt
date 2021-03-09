package com.example.splitreceipt.dto;

import com.example.splitreceipt.domain.Receipt;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Behzod on 09, March, 2021
 */
public class ReceiptResponseDto extends ResponseDto {
    @JsonProperty("comment")
    private String comment;

    @JsonProperty("amount")
    private Double amount;

    public ReceiptResponseDto() {
    }

    public ReceiptResponseDto(Receipt receipt) {
        this.setId(receipt.getId());
        this.setComment(receipt.getComment());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
