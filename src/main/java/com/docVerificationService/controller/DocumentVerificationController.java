package com.docVerificationService.controller;

import com.docVerificationService.model.DocumentVerificationEntity;
import com.docVerificationService.repositery.DocumentVerificationRepository;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentVerificationController {
    private final DocumentVerificationRepository repo;

    @GetMapping({"/{documentId}"})
    public ResponseEntity<DocumentVerificationEntity> getStatus(@PathVariable UUID documentId) {
        return (ResponseEntity)this.repo.findByDocumentId(documentId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public DocumentVerificationController(final DocumentVerificationRepository repo) {
        this.repo = repo;
    }
}
