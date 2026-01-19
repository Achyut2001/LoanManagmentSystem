package com.LoanManagementSystem.globalExceptionHandller;

import com.LoanManagementSystem.apireponse.ApiError;
import com.LoanManagementSystem.globalExceptionHandller.customeException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(DocumentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedDocumentAccessException.class)
    public ResponseEntity<ApiError> handleForbidden(UnauthorizedDocumentAccessException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(S3AccessException.class)
    public ResponseEntity<ApiError> handleS3(S3AccessException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(S3UploadException.class)
    public ResponseEntity<ApiError> handleS3Upload(S3UploadException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(S3DownloadException.class)
    public ResponseEntity<ApiError> handleS3Download(S3DownloadException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ApiError(ex.getMessage()));
    }
}
