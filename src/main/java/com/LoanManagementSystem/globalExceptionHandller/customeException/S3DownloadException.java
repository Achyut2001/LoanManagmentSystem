package com.LoanManagementSystem.globalExceptionHandller.customeException;

public class S3DownloadException extends RuntimeException{

    public S3DownloadException(String message) {
        super(message);
    }

    public S3DownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
