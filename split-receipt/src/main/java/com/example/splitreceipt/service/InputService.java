package com.example.splitreceipt.service;

import com.example.splitreceipt.domain.Input;
import com.example.splitreceipt.dto.InputCreateDto;
import com.example.splitreceipt.exception.ValidationException;
import com.example.splitreceipt.repository.InputRepository;
import com.example.splitreceipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Service
public class InputService {

    private final InputRepository inputRepository;

    @Autowired
    public InputService (
            InputRepository inputRepository
    ) {
        this.inputRepository = inputRepository;
    }

    public Input create(InputCreateDto inputCreateDto) {
        if (!isValid(inputCreateDto)) throw new ValidationException("Input is not valid");

        Input input = new Input(inputCreateDto);

        return inputRepository.save(input);
    }

    private Boolean isValid(InputCreateDto inputCreateDto) {
        if (inputCreateDto.getReceiptId() == null) return false;
        //if less than 500 soums or 50 000 tiyins
        if (inputCreateDto.getAmount() == null) return false;

        if (inputCreateDto.getUserId() == null) return false;

        if (inputCreateDto.getComment() == null || inputCreateDto.getComment().length() > 500) return false;

        return true;
    }
}
