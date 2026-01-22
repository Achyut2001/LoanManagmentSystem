package com.docVerificationService.model;

import jakarta.persistence.*;
import lombok.*;
import com.docVerificationService.model.DocumentType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "document_verification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentVerificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID documentId;
    private Long loanId;
    private String customerId;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    private VerificationStatus status;

    private String verifiedBy;   // THIRD_PARTY
    private String remarks;

    private Instant verifiedAt;
}
