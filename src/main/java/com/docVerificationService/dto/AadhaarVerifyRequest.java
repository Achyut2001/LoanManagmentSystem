package com.docVerificationService.dto;

public class AadhaarVerifyRequest {
    private String aadhaarNumber;

    public void setAadhaarNumber(final String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getAadhaarNumber() {
        return this.aadhaarNumber;
    }

    public AadhaarVerifyRequest() {
    }
}
