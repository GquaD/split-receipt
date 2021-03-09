package com.example.splitreceipt.dto;

import com.example.splitreceipt.domain.Input;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Behzod on 09, March, 2021
 */
public class InputsOfReceiptDto extends ReceiptResponseDto {
    @JsonProperty("inputs")
    private List<InputDto> inputs;

    public InputsOfReceiptDto(ReceiptResponseDto dto, List<InputDto> inputDtoList) {
        this.setId(dto.getId());
        this.setComment(dto.getComment());
        this.setAmount(dto.getAmount());
        this.inputs = inputDtoList;
    }

    public List<InputDto> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputDto> inputs) {
        this.inputs = inputs;
    }
}
