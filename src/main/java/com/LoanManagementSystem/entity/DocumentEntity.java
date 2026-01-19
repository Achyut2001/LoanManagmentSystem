package com.LoanManagementSystem.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "document_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String customerId;

    private long loanId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType docType;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String s3Key;

    private Long fileSize;

    private String contentType;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private String remarks;

    private String uploadedBy;

    private Instant uploadedAt;

    private Instant updatedAt;
}
