package com.LoanManagementSystem.serviceImpl;

import com.LoanManagementSystem.entity.DocumentEntity;
import com.LoanManagementSystem.entity.DocumentStatus;
import com.LoanManagementSystem.entity.DocumentType;
import com.LoanManagementSystem.entity.dto.DocType;
import com.LoanManagementSystem.entity.dto.DocumentResponseDTO;
import com.LoanManagementSystem.entity.dto.DocumentResponseEvent;
//import com.LoanManagementSystem.avro.DocumentResponseEvent;
import com.LoanManagementSystem.fileValidate.DocumentValidator;
import com.LoanManagementSystem.globalExceptionHandller.customeException.DocumentNotFoundException;
import com.LoanManagementSystem.repositery.DocumentUploadRepositery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.LoanManagementSystem.util.S3Util;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentUploadServiceImp {

    private final DocumentUploadRepositery repo;
    private final S3Util s3Util;
    private final ModelMapper mapper;
    private final KafkaTemplate<String, DocumentResponseEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.document-uploaded}")
    private String documentUploadedTopic;

    @Transactional
    public DocumentResponseDTO uploadDocument(MultipartFile file,
                                              String customerId,
                                              long loanId,
                                              DocumentType docType) {

        DocumentValidator.validateFileType(file);
        UUID documentId = UUID.randomUUID();
        //    String extension = getExtension(file.getOriginalFilename());

        String s3Key = String.format(
                "loan-documents/%s/%s/%s",
                loanId,
                customerId,
                file.getOriginalFilename()
        );

        s3Util.uploadFile(s3Key, file);

        var entity = DocumentEntity.builder()
                .id(documentId)
                .customerId(customerId)
                .loanId(loanId)
                .docType(docType)
                .filename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .s3Key(s3Key)
                .status(DocumentStatus.UPLOADED)
                .uploadedAt(Instant.now())
                .build();

        repo.save(entity);

        DocumentResponseEvent event = DocumentResponseEvent.newBuilder()
                .setDocumentId(entity.getId().toString())
                .setCustomerId(entity.getCustomerId())
                .setLoanId(entity.getLoanId())
                .setDocType(DocType.valueOf(entity.getDocType().name()))
                .setFilename(entity.getFilename())
                .setContentType(entity.getContentType())
                .setFileSize(entity.getFileSize())
                .setS3Key(entity.getS3Key())
                .setStatus(com.LoanManagementSystem.entity.dto.DocumentStatus.valueOf(entity.getStatus().name()))
                .setUploadedAt(entity.getUploadedAt().toString())
                .build();

        kafkaTemplate.send(documentUploadedTopic, entity.getId().toString(), event);

        log.info("Document upload event published to Kafka: {}", event);


        return mapper.map(entity, DocumentResponseDTO.class);

    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "bin";
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    public String getDownloadUrl(Long loanId, UUID documentId) {

        DocumentEntity document = repo.findByIdAndLoanId(documentId, loanId)
                .orElseThrow(() ->
                        new DocumentNotFoundException("Document not found"));

        return s3Util.generatePreSignedDownloadUrl(
                document.getS3Key(),
                5
        );
    }
}



