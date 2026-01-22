package com.docVerificationService.aadharclient;

import com.docVerificationService.dto.AadhaarVerifyRequest;
import com.docVerificationService.dto.AadhaarVerifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "aadhaarClient",
        url = "${aadhaar.api.url}"
)
public interface AadhaarVerificationClient {

    @PostMapping("/validate/aadhaar")
    AadhaarVerifyResponse verifyAadhaar(
            @RequestHeader("api-token") String apiKey,
            @RequestBody AadhaarVerifyRequest request
    );
}
