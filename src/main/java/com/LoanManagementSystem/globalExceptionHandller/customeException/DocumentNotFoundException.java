package com.LoanManagementSystem.globalExceptionHandller.customeException;

public class DocumentNotFoundException extends RuntimeException{

    public DocumentNotFoundException(String message) {
        super(message);
    }
}
