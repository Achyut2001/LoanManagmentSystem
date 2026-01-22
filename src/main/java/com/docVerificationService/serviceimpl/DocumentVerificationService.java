package com.docVerificationService.serviceimpl;

import com.docVerificationService.aadharclient.AadhaarVerificationClient;
import com.docVerificationService.dto.AadhaarVerifyRequest;
import com.docVerificationService.dto.AadhaarVerifyResponse;
import com.docVerificationService.dto.DocumentResponseEvent;
import com.docVerificationService.model.DocumentVerificationEntity;
import com.docVerificationService.model.VerificationStatus;
import com.docVerificationService.repositery.DocumentVerificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DocumentVerificationService {

    private final ModelMapper mapper;
    private final AadhaarVerificationClient aadhaarVerificationClient;
    private final DocumentVerificationRepository documentVerificationRepository;

    @Value("${aadhaar.api.key}")
    private String apiKey;

    public DocumentVerificationService(
            ModelMapper mapper,
            AadhaarVerificationClient aadhaarVerificationClient,
            DocumentVerificationRepository documentVerificationRepository) {
        this.mapper = mapper;
        this.aadhaarVerificationClient = aadhaarVerificationClient;
        this.documentVerificationRepository = documentVerificationRepository;
    }

    public void verifyDocument(DocumentResponseEvent dto) {

        AadhaarVerifyRequest request = new AadhaarVerifyRequest();
        request.setAadhaarNumber("12345678");

        AadhaarVerifyResponse response =
                aadhaarVerificationClient.verifyAadhaar(apiKey, request);

        VerificationStatus status =
                response.isValid() ? VerificationStatus.VERIFIED : VerificationStatus.FAILED;

        DocumentVerificationEntity entity =
                mapper.map(dto, DocumentVerificationEntity.class);

        entity.setStatus(status);
        entity.setVerifiedBy("THIRD_PARTY");

        documentVerificationRepository.save(entity);
    }
}


