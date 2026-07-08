package com.medilabo.front.service;

import com.medilabo.front.model.AssessmentResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AssessmentService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gateway.url}")
    private String gatewayUrl;

    public AssessmentResult getPatientRisk(Long patientId) {
        return restTemplate.getForObject(
                gatewayUrl + "/assessments/patient/" + patientId + "/risk",
                AssessmentResult.class
        );
    }
}