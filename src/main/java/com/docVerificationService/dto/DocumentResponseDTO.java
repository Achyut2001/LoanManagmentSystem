package com.docVerificationService.dto;

import com.docVerificationService.model.DocumentStatus;
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
public class DocumentResponseDTO {
    private UUID id;
    private String customerId;
    private long loanId;
    private String filename;
    private DocumentStatus status;
    private String remarks;
    private String uploadedBy;
    private Instant uploadedAt;
}
