package com.example.splitreceipt.exception;

/**
 * Created by Behzod on 08, March, 2021
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
