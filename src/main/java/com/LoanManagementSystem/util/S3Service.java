package com.LoanManagementSystem.util;

import com.LoanManagementSystem.globalExceptionHandller.customeException.S3DownloadException;
import com.LoanManagementSystem.globalExceptionHandller.customeException.S3UploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "aws.s3.bucket")
public class S3Service {

    private final S3Client s3Client;
    private String bucketName;

    public void uploadFile(MultipartFile file, String s3Key) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request,
                    RequestBody.fromBytes(file.getBytes()));

        } catch (IOException e) {
            throw new S3UploadException("Failed to read file content", e);
        } catch (Exception e) {
            throw new S3UploadException("Failed to upload file to S3", e);
        }
    }

    public byte[] downloadFile(String s3Key) {
        try {
            ResponseBytes<GetObjectResponse> objectBytes =
                    s3Client.getObjectAsBytes(
                            GetObjectRequest.builder()
                                    .bucket(bucketName)
                                    .key(s3Key)
                                    .build()
                    );

            return objectBytes.asByteArray();

        } catch (Exception e) {
            throw new S3DownloadException("Failed to download file from S3", e);
        }
    }


}
