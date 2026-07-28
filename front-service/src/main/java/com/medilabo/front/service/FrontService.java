package com.medilabo.front.service;

import com.medilabo.front.model.Front;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class FrontService {

    private final RestTemplate restTemplate;
    private final String gatewayUrl;

    public FrontService(
            RestTemplate restTemplate,
            @Value("${gateway.url}") String gatewayUrl) {

        this.restTemplate = restTemplate;
        this.gatewayUrl = gatewayUrl;
    }

    public List<Front> getAllPatients() {
        Front[] patients = restTemplate.getForObject(
                gatewayUrl + "/patients",
                Front[].class
        );

        if (patients == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(patients);
    }

    public Front getPatientById(Long patientId) {
        Front patient = restTemplate.getForObject(
                gatewayUrl + "/patients/" + patientId,
                Front.class
        );

        if (patient == null) {
            throw new IllegalStateException(
                    "Le patient " + patientId + " n'a pas été trouvé."
            );
        }

        return patient;
    }

    public Front addPatient(Front patient) {
        Front createdPatient = restTemplate.postForObject(
                gatewayUrl + "/patients",
                patient,
                Front.class
        );

        if (createdPatient == null) {
            throw new IllegalStateException(
                    "Le patient n'a pas pu être créé."
            );
        }

        return createdPatient;
    }

    public void updatePatient(Long patientId, Front patient) {
        restTemplate.put(
                gatewayUrl + "/patients/" + patientId,
                patient
        );
    }
}