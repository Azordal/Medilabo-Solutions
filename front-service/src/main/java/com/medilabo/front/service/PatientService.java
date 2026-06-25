package com.medilabo.front.service;

import com.medilabo.front.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PatientService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gateway.url}")
    private String gatewayUrl;

    public List<Patient> getAllPatients() {
        Patient[] patients = restTemplate.getForObject(
                gatewayUrl + "/patients",
                Patient[].class
        );

        return Arrays.asList(patients);
    }

    public Patient getPatientById(Long id) {
        return restTemplate.getForObject(
                gatewayUrl + "/patients/" + id,
                Patient.class
        );
    }

    public void addPatient(Patient patient) {
        restTemplate.postForObject(
                gatewayUrl + "/patients",
                patient,
                Patient.class
        );
    }

    public void updatePatient(Long id, Patient patient) {
        restTemplate.put(
                gatewayUrl + "/patients/" + id,
                patient
        );
    }
}