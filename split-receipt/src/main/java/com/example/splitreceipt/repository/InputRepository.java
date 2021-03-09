package com.example.splitreceipt.repository;

import com.example.splitreceipt.domain.Input;
import com.example.splitreceipt.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Repository
public interface InputRepository extends JpaRepository<Input, Integer> {
    List<Input> findAllByReceipt(Receipt receipt);
    List<Input> findAllByReceiptId(Integer id);
}
