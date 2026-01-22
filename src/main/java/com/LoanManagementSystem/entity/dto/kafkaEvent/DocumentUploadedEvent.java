package com.LoanManagementSystem.entity.dto.kafkaEvent;

import com.LoanManagementSystem.entity.DocumentStatus;
import com.LoanManagementSystem.entity.DocumentType;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentUploadedEvent {

    private UUID documentId;
    private String customerId;
    private long loanId;
    private DocumentType docType;
    private String filename;
    private String s3Key;
    private Long fileSize;
    private String contentType;
    private DocumentStatus status;
    private Instant uploadedAt;
}
