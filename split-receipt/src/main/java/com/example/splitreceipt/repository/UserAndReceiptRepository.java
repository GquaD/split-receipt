package com.example.splitreceipt.repository;

import com.example.splitreceipt.domain.UserAndReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Behzod on 08, March, 2021
 */
@Repository
public interface UserAndReceiptRepository extends JpaRepository<UserAndReceipt, Integer> {
}
