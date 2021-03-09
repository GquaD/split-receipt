package com.example.splitreceipt.service;

import com.example.splitreceipt.domain.Input;
import com.example.splitreceipt.domain.Receipt;
import com.example.splitreceipt.dto.InputDto;
import com.example.splitreceipt.dto.InputsOfReceiptDto;
import com.example.splitreceipt.dto.ReceiptCreateDto;
import com.example.splitreceipt.dto.ReceiptResponseDto;
import com.example.splitreceipt.exception.ResourceNotFoundException;
import com.example.splitreceipt.exception.ValidationException;
import com.example.splitreceipt.repository.InputRepository;
import com.example.splitreceipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Service
public class ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final InputRepository inputRepository;
    private final CalculationsService calculationsService;

    @Autowired
    public ReceiptService (
            ReceiptRepository receiptRepository,
            InputRepository inputRepository,
            CalculationsService calculationsService
    ) {
        this.receiptRepository = receiptRepository;
        this.inputRepository = inputRepository;
        this.calculationsService = calculationsService;
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


    public InputsOfReceiptDto getInputsByReceipt(Integer receiptId) {
        ReceiptResponseDto dto = this.getReceiptWithOverallAmount(receiptId);

        List<Input> inputs = inputRepository.findAllByReceiptId(receiptId);

        Map<Integer, Double> map = calculationsService.calculateOverallAmountPerUser(inputs);

        List<InputDto> inputDtoList = mapAmountsToInputDtos(map);

        return new InputsOfReceiptDto(dto, inputDtoList);
    }

    private List<InputDto> mapAmountsToInputDtos(Map<Integer, Double> map) {
        List<InputDto> inputDtoList = new ArrayList<>();

        for (Integer key : map.keySet()) {
            InputDto inputDto = new InputDto();
            inputDto.setUserId(key);
            inputDto.setOverallAmount(map.get(key));
            inputDtoList.add(inputDto);
        }
        return inputDtoList;
    }
}
