package com.example.splitreceipt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Behzod on 08, March, 2021
 */
public class UserCreateDto {
    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
