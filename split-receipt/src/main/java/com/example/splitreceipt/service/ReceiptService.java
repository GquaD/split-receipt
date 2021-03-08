package com.example.splitreceipt.service;

import com.example.splitreceipt.domain.Receipt;
import com.example.splitreceipt.dto.ReceiptCreateDto;
import com.example.splitreceipt.exception.ValidationException;
import com.example.splitreceipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Service
public class ReceiptService {
    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptService (
            ReceiptRepository receiptRepository
    ) {
        this.receiptRepository = receiptRepository;
    }

    public Integer create(ReceiptCreateDto receiptCreateDto) {
        if(!isValid(receiptCreateDto)) throw new ValidationException("Input is not valid");
        Receipt receipt = new Receipt(receiptCreateDto);
        return receiptRepository.save(receipt).getId();
    }

    private Boolean isValid(ReceiptCreateDto receiptCreateDto) {
        return receiptCreateDto.getComment() != null;
    }
}
