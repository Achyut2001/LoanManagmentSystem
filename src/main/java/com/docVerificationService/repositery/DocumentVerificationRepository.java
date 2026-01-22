package com.docVerificationService.repositery;


import com.docVerificationService.model.DocumentVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DocumentVerificationRepository extends JpaRepository<DocumentVerificationEntity,Long> {
    Optional<DocumentVerificationEntity> findByDocumentId(UUID documentId);
}
