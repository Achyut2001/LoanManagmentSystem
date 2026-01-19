package com.LoanManagementSystem.entity.dto;

import com.LoanManagementSystem.entity.DocumentStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class DocumentResponseDTO {

    private UUID id;
    private String customerId;
    private long loanId;
    private String filename;
    private DocumentStatus status;
    private String remarks;
    private String uploadedBy;
    private Instant uploadedAt;

    @Override
    public String toString() {
        return "DocumentResponseDTO{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", loanId=" + loanId +
                ", filename='" + filename + '\'' +
                ", status=" + status +
                ", remarks='" + remarks + '\'' +
                ", uploadedBy='" + uploadedBy + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
