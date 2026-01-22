package com.docVerificationService.consumer;

import com.docVerificationService.dto.DocumentResponseEvent;
import com.docVerificationService.serviceimpl.DocumentVerificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DocumentUploadedConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(DocumentUploadedConsumer.class);

    private final DocumentVerificationService documentVerificationService;
    private final ObjectMapper objectMapper;

    public DocumentUploadedConsumer(
            DocumentVerificationService documentVerificationService,
            ObjectMapper objectMapper
    ) {
        this.documentVerificationService = documentVerificationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.document-uploaded}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String message) {

        log.info("Kafka message received: {}", message);

        try {
            DocumentResponseEvent event =
                    objectMapper.readValue(message, DocumentResponseEvent.class);
            documentVerificationService.verifyDocument(event);

        } catch (Exception e) {
            log.error("Error while processing Kafka message: {}", message, e);
        }
    }
}
