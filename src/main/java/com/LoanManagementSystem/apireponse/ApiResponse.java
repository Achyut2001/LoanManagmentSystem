package com.LoanManagementSystem.apireponse;

import lombok.Builder;

@Builder
public record ApiResponse<T>(boolean success, String message, T data) {

    public static <T> ApiResponse<T> ok(String msg, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(msg)
                .data(data)
                .build();
    }
}