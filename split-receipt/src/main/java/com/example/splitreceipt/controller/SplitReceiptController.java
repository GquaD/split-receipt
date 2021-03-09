package com.example.splitreceipt.controller;

import com.example.splitreceipt.domain.Input;
import com.example.splitreceipt.domain.User;
import com.example.splitreceipt.dto.*;
import com.example.splitreceipt.exception.ResourceNotFoundException;
import com.example.splitreceipt.exception.ValidationException;
import com.example.splitreceipt.service.CalculationsService;
import com.example.splitreceipt.service.InputService;
import com.example.splitreceipt.service.ReceiptService;
import com.example.splitreceipt.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@RestController
@RequestMapping("/receipt")
public class SplitReceiptController {

    private static Logger logger = LogManager.getLogger(SplitReceiptController.class);

    private final InputService inputService;
    private final ReceiptService receiptService;
    private final UserService userService;
    private final CalculationsService calculationsService;

    @Autowired
    public SplitReceiptController(
            InputService inputService,
            ReceiptService receiptService,
            UserService userService,
            CalculationsService calculationsService
    ) {
        this.inputService = inputService;
        this.receiptService = receiptService;
        this.userService = userService;
        this.calculationsService = calculationsService;
    }

    @PostMapping("/input")
    public ResponseEntity<?> createInput(@RequestBody InputCreateDto inputCreateDto) {
        Input input = null;
        try {
            input = inputService.create(inputCreateDto);
        } catch (ValidationException e) {
            logger.error("Error on input creation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResultDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
        }
        return ResponseEntity.ok(input);
    }

    @PostMapping("")
    public ResponseEntity<?> createReceipt(@RequestBody ReceiptCreateDto receiptCreateDto) {
        ResponseDto result = new ResponseDto();
        try {
            Integer id = receiptService.create(receiptCreateDto);
            result.setId(id);
        } catch (ValidationException e) {
            logger.error("Error on input creation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResultDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userCreateDto) {
        ResponseDto result = new ResponseDto();
        try {
            Integer id = userService.create(userCreateDto);
            result.setId(id);
        } catch (ValidationException e) {
            logger.error("Error on input creation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResultDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(
            @PathVariable Integer id) {
        User user = null;
        try {
            user = userService.getById(id);
        } catch (ResourceNotFoundException e) {
            logger.error("Error on input creation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResultDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage()));
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/who-owes-me")
    public ResponseEntity<?> getWhoOwesMe(
            @RequestBody InputCreateDto inputCreateDto) {
        List<DebtorDto> debtors = new ArrayList<>();
        try {
            debtors = calculationsService.calculateDebtors(inputCreateDto);
        } catch (ValidationException e) {
            logger.error("Error on input creation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResultDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
        }
        return ResponseEntity.ok(debtors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOverallAmountOfReceipt(
            @PathVariable("id") Integer receiptId
    ) {
        ReceiptResponseDto result;
        try {
            result = receiptService.getReceiptWithOverallAmount(receiptId);
        } catch (ValidationException e) {
            logger.error("Error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResultDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
        } catch (ResourceNotFoundException e) {
            logger.error("Error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResultDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage()));
        }
        return ResponseEntity.ok(result);
    }
}
