package com.LoanManagementSystem.util;

import com.LoanManagementSystem.globalExceptionHandller.customeException.S3UploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;

@Component
public class S3Util {
    private final S3Client s3Client;
    private final String bucketName;
    private final S3Presigner s3Presigner;

    public S3Util(
            S3Client s3Client,
            @Value("${aws.s3.bucket}") String bucketName, S3Presigner s3Presigner
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.s3Presigner = s3Presigner;
    }

    public void uploadFile(String s3Key, MultipartFile file) {
        try {
            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putReq, RequestBody.fromBytes(file.getBytes()));

        } catch (Exception e) {
            throw new S3UploadException("Failed to upload file to S3", e);
        }
    }

    public String generatePreSignedDownloadUrl(String s3Key, int expiryMinutes) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .getObjectRequest(getObjectRequest)
                        .signatureDuration(Duration.ofMinutes(expiryMinutes))
                        .build();

        return s3Presigner
                .presignGetObject(presignRequest)
                .url()
                .toString();
    }
}
