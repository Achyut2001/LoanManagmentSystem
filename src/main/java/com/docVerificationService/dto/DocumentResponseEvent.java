package com.docVerificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponseEvent {
    private UUID documentId;
    private String customerId;
    private long loanId;
    private String docType;
    private String filename;
    private String s3Key;
    private Long fileSize;
    private String contentType;
    private String status;
    private Instant uploadedAt;
}
