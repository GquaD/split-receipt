package com.example.splitreceipt.service;

import com.example.splitreceipt.domain.Input;
import com.example.splitreceipt.domain.Receipt;
import com.example.splitreceipt.dto.ReceiptCreateDto;
import com.example.splitreceipt.dto.ReceiptResponseDto;
import com.example.splitreceipt.exception.ResourceNotFoundException;
import com.example.splitreceipt.exception.ValidationException;
import com.example.splitreceipt.repository.InputRepository;
import com.example.splitreceipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Service
public class ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final InputRepository inputRepository;

    @Autowired
    public ReceiptService (
            ReceiptRepository receiptRepository,
            InputRepository inputRepository
    ) {
        this.receiptRepository = receiptRepository;
        this.inputRepository = inputRepository;
    }

    public Integer create(ReceiptCreateDto receiptCreateDto) {
        if(!isValid(receiptCreateDto)) throw new ValidationException("Input is not valid");
        Receipt receipt = new Receipt(receiptCreateDto);
        return receiptRepository.save(receipt).getId();
    }

    private Boolean isValid(ReceiptCreateDto receiptCreateDto) {
        return receiptCreateDto.getComment() != null;
    }

    public ReceiptResponseDto getReceiptWithOverallAmount(Integer receiptId) {
        Receipt receipt = getReceipt(receiptId);

        List<Input> inputs = inputRepository.findAllByReceiptId(receiptId);

        ReceiptResponseDto result = new ReceiptResponseDto(receipt);

        Double amount = calculateOverallAmount(inputs);

        result.setAmount(amount);

        return result;
    }

    private Receipt getReceipt(Integer receiptId) {
        if (receiptId == null) throw new ValidationException("Receipt id is null");

        Receipt receipt = receiptRepository.getOne(receiptId);

        if (receipt == null) throw new ResourceNotFoundException("Receipt does not exist");
        return receipt;
    }

    private Double calculateOverallAmount(List<Input> inputs) {
        return inputs.stream().mapToDouble(Input::getAmount).sum();
    }
}
