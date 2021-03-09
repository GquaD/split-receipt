package com.example.splitreceipt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Behzod on 09, March, 2021
 */
public class InputDto {
    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("overall_amount")
    private Double overallAmount;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getOverallAmount() {
        return overallAmount;
    }

    public void setOverallAmount(Double overallAmount) {
        this.overallAmount = overallAmount;
    }
}
