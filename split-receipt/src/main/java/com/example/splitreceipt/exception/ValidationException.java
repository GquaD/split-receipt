package com.example.splitreceipt.exception;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
public class ValidationException extends RuntimeException{
    public ValidationException(String errorMessage){
        super(errorMessage);
    }
}
