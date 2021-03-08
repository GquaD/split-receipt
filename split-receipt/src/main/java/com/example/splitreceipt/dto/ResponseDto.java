package com.example.splitreceipt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Behzod on 08, March, 2021
 */
public class ResponseDto {
    @JsonProperty("id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
