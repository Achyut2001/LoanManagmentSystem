package com.LoanManagementSystem.globalExceptionHandller.customeException;

public class UnauthorizedDocumentAccessException extends RuntimeException {
    public UnauthorizedDocumentAccessException(String message) {
        super(message);
    }
}
