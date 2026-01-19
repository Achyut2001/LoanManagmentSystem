package com.LoanManagementSystem.globalExceptionHandller.customeException;

public class S3AccessException extends RuntimeException{
    public S3AccessException(String message) {
        super(message);
    }
}
