package com.example.splitreceipt.repository;

import com.example.splitreceipt.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    @Query(nativeQuery = true,
            value = "SELECT *\n" +
                    "from receipt r\n" +
                    "where r.id = :id")
    public Optional<Receipt> findById(@Param("id")Integer id);
}
