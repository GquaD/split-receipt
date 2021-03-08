package com.example.splitreceipt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Behzod on 08, March, 2021
 */
public class DebtorDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("amount")
    private Double amount;

    public DebtorDto(){

    }

    public DebtorDto(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
