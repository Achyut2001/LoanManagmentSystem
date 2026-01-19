package com.LoanManagementSystem.controller;

import com.LoanManagementSystem.apireponse.ApiResponse;
import com.LoanManagementSystem.entity.DocumentType;
import com.LoanManagementSystem.entity.dto.DocumentResponseDTO;
import com.LoanManagementSystem.entity.dto.DownloadResponseDTO;
import com.LoanManagementSystem.serviceImpl.DocumentUploadServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DocumentUploadController {

    private final DocumentUploadServiceImp documentService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<DocumentResponseDTO>> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("customerId") String customerId,
            @RequestParam(value = "loanId", required = false) long loanId,
            @RequestParam("docType") DocumentType docType
    ) {
        log.info(
                "Document upload request received | customerId={} | loanId={} | docType={} | fileName={} | fileSize={} bytes",
                customerId,
                loanId,
                docType,
                file.getOriginalFilename(),
                file.getSize()
        );
        DocumentResponseDTO dto =
                documentService.uploadDocument(file, customerId, loanId, docType);

        /*log.info(
                "Document uploaded successfully | customerId={} | loanId={} | status={}",
                customerId,
                loanId,
                dto.getStatus()
        );*/
        return ResponseEntity.ok(
                ApiResponse.ok("Document uploaded successfully", dto)
        );
    }

    @GetMapping("/{loanId}/documents/{documentId}/download")
    public ResponseEntity<DownloadResponseDTO> download(
            @PathVariable Long loanId,
            @PathVariable UUID documentId) {

        String url = documentService.getDownloadUrl(loanId, documentId);
        return ResponseEntity.ok(new DownloadResponseDTO(url));
    }

}
