package com.example.splitreceipt.repository;

import com.example.splitreceipt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
