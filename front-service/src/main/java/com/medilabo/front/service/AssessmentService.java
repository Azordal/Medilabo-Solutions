package com.medilabo.front.service;

import com.medilabo.front.model.AssessmentResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AssessmentService {

    private final RestTemplate restTemplate;
    private final String gatewayUrl;

    public AssessmentService(
            RestTemplate restTemplate,
            @Value("${gateway.url}") String gatewayUrl) {

        this.restTemplate = restTemplate;
        this.gatewayUrl = gatewayUrl;
    }

    public AssessmentResult getPatientRisk(Long patientId) {

        AssessmentResult assessmentResult = restTemplate.getForObject(
                gatewayUrl
                        + "/assessments/patient/"
                        + patientId
                        + "/risk",
                AssessmentResult.class
        );

        if (assessmentResult == null) {
            throw new IllegalStateException(
                    "L'évaluation du risque du patient "
                            + patientId
                            + " n'a pas pu être récupérée."
            );
        }

        return assessmentResult;
    }
}