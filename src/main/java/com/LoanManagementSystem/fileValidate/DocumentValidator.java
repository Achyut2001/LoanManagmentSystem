package com.LoanManagementSystem.fileValidate;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;

public class DocumentValidator {

    private static final Tika tika = new Tika();
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "application/pdf",
            "image/jpeg",
            "image/jpg",
            "image/png"
    );

    public static void validateFileType(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file must not be empty");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size must be less than or equal to 5 MB");
        }
        try {
            String detectedType = tika.detect(file.getInputStream());
            if (!ALLOWED_MIME_TYPES.contains(detectedType)) {
                throw new IllegalArgumentException(
                        "Invalid file type. Allowed types: PDF, JPG, JPEG,PNG"
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("File validation failed", e);
        }
    }
}
