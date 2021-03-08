package com.example.splitreceipt.service;

import com.example.splitreceipt.domain.User;
import com.example.splitreceipt.dto.ReceiptCreateDto;
import com.example.splitreceipt.dto.UserCreateDto;
import com.example.splitreceipt.exception.ResourceNotFoundException;
import com.example.splitreceipt.exception.ValidationException;
import com.example.splitreceipt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public Integer create(UserCreateDto userCreateDto) {
        if (!isValid(userCreateDto)) throw new ValidationException("Input is not valid");
        User user = new User(userCreateDto);
        return userRepository.save(user).getId();
    }

    private Boolean isValid(UserCreateDto userCreateDto) {
        return userCreateDto.getName() != null;
    }

    public User getById(Integer id) {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) throw new ResourceNotFoundException("User with id: " + id + " not found");
        return optional.get();
    }
}
