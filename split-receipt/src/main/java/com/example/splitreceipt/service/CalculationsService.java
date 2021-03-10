package com.example.splitreceipt.service;

import com.example.splitreceipt.domain.Input;
import com.example.splitreceipt.domain.Receipt;
import com.example.splitreceipt.domain.User;
import com.example.splitreceipt.dto.DebtorDto;
import com.example.splitreceipt.dto.InputCreateDto;
import com.example.splitreceipt.dto.ReceiptResponseDto;
import com.example.splitreceipt.exception.ResourceNotFoundException;
import com.example.splitreceipt.exception.ValidationException;
import com.example.splitreceipt.repository.InputRepository;
import com.example.splitreceipt.repository.ReceiptRepository;
import com.example.splitreceipt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Behzod on 08, March, 2021
 */
@Service
public class CalculationsService {
    private final InputRepository inputRepository;
    private final UserRepository userRepository;
    private final ReceiptRepository receiptRepository;

    @Autowired
    public CalculationsService (
            InputRepository inputRepository,
            UserRepository userRepository,
            ReceiptRepository receiptRepository
    ) {
        this.inputRepository = inputRepository;
        this.userRepository = userRepository;
        this.receiptRepository = receiptRepository;
    }

    public List<DebtorDto> calculateDebtors(InputCreateDto inputCreateDto) {
        if (!isValid(inputCreateDto)) throw new ValidationException("Input is incorrect");

        Receipt receipt = receiptRepository.findById(inputCreateDto.getReceiptId()).get();
        List<Input> inputs = inputRepository.findAllByReceipt(receipt);

        //sum of all amounts for the receipt
        Double sum = calculateSumForReceipt(inputs);

        List<User> users = retrieveUsers(inputs);

        Double mean = calculateMean(sum, users.size());

        //removing current user from the list of all users who contributed to the receipt
        users = users.stream().filter(u -> !u.getId().equals(inputCreateDto.getUserId())).collect(Collectors.toList());

        Map<Integer, Double> amountsOverallMap = calculateOverallAmountPerUser(inputs);

        Double givenUserOverallAmount = amountsOverallMap.get(inputCreateDto.getUserId());
        amountsOverallMap.remove(inputCreateDto.getUserId());

        Double givenUserDifferenceFromMean = givenUserOverallAmount - mean;

        if (givenUserDifferenceFromMean <= 0 ) return new ArrayList<>();

        List<DebtorDto> debtors = findDifferences(amountsOverallMap, users, mean);

        debtors = findDebtsOfAllUsersToGivenUser(debtors, givenUserDifferenceFromMean);

        return debtors;
    }

    private List<DebtorDto> findDebtsOfAllUsersToGivenUser(List<DebtorDto> debtors, Double givenUserDifferenceFromMean) {
        if (givenUserDifferenceFromMean > 0) {
            Double debtorsAmountsSum = debtors.stream().mapToDouble(DebtorDto::getAmount).sum();
            for (DebtorDto debtor : debtors) {
                Double debtToGivenUser = (givenUserDifferenceFromMean / debtorsAmountsSum) * debtor.getAmount();
                debtToGivenUser = BigDecimal.valueOf(debtToGivenUser).setScale(4, RoundingMode.HALF_UP).doubleValue();
                debtor.setAmount(debtToGivenUser);
            }
        }
        return debtors;
    }

    private List<DebtorDto> findDifferences(Map<Integer, Double> amountsOverallMap, List<User> users, Double mean) {
        List<DebtorDto> debtors = new ArrayList<>();
        for (Integer key: amountsOverallMap.keySet()) {
            Double difference = mean - amountsOverallMap.get(key);
            if (difference > 0) {
                DebtorDto debtorDto = new DebtorDto();
                debtorDto.setAmount(difference);
                debtorDto.setName(users.stream().filter(i -> i.getId().equals(key)).findFirst().get().getName());
                debtors.add(debtorDto);
            }
        }
        return debtors;
    }

    private List<DebtorDto> findDifferencesForMyDebts(Map<Integer, Double> amountsOverallMap, List<User> users, Double mean) {
        List<DebtorDto> debtors = new ArrayList<>();
        for (Integer key: amountsOverallMap.keySet()) {
            Double difference = mean - amountsOverallMap.get(key);
            if (difference <= 0) {
                DebtorDto debtorDto = new DebtorDto();
                debtorDto.setAmount(difference);
                debtorDto.setName(users.stream().filter(i -> i.getId().equals(key)).findFirst().get().getName());
                debtors.add(debtorDto);
            }
        }
        return debtors;
    }

    public Map<Integer, Double> calculateOverallAmountPerUser(List<Input> inputs) {
        Map<Integer, Double> amountsOverallMap = new HashMap<>();
        for (Input input : inputs) {
            Integer tempUserId = input.getUser().getId();
            amountsOverallMap.merge(tempUserId, input.getAmount(), Double::sum);
        }
        return amountsOverallMap;
    }

    private Double calculateMean(Double sum, int size) {
        return BigDecimal.valueOf(sum / (size * 1.0)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private List<User> retrieveUsers(List<Input> inputs) {
        List<User> users = new ArrayList<>();
        for (Input input : inputs) {
            users.add(input.getUser());
        }
        users = users.stream().distinct().collect(Collectors.toList());
        return users;
    }

    private Double calculateSumForReceipt(List<Input> inputs) {
        Double result = 0.0;
        for (Input input : inputs) {
            result += input.getAmount();
        }
        return result;
    }

    private Boolean isValid(InputCreateDto inputCreateDto) {
        if (inputCreateDto.getReceiptId() == null) return false;

        if (inputCreateDto.getUserId() == null) return false;

        return true;
    }

    public List<DebtorDto> calculateMyDebts(InputCreateDto inputCreateDto) {
        if (!isValid(inputCreateDto)) throw new ValidationException("Input is incorrect");

        Receipt receipt = receiptRepository.findById(inputCreateDto.getReceiptId()).get();
        List<Input> inputs = inputRepository.findAllByReceipt(receipt);

        //sum of all amounts for the receipt
        Double sum = calculateSumForReceipt(inputs);

        List<User> users = retrieveUsers(inputs);

        Double mean = calculateMean(sum, users.size());

        //removing current user from the list of all users who contributed to the receipt
        users = users.stream().filter(u -> !u.getId().equals(inputCreateDto.getUserId())).collect(Collectors.toList());

        Map<Integer, Double> amountsOverallMap = calculateOverallAmountPerUser(inputs);

        Double givenUserOverallAmount = amountsOverallMap.get(inputCreateDto.getUserId());
        amountsOverallMap.remove(inputCreateDto.getUserId());

        Double givenUserDifferenceFromMean = mean - givenUserOverallAmount;

        if (givenUserDifferenceFromMean <= 0 ) return new ArrayList<>();

        List<DebtorDto> debtors = findDifferencesForMyDebts(amountsOverallMap, users, mean);

        debtors = findDebtsOfAllUsersToGivenUser(debtors, givenUserDifferenceFromMean);

        return debtors;
    }
}
