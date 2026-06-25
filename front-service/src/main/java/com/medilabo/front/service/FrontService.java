package com.medilabo.front.service;

import com.medilabo.front.model.Front;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FrontService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gateway.url}")
    private String gatewayUrl;

    public List<Front> getAllPatients() {
        Front[] patients = restTemplate.getForObject(
                gatewayUrl + "/patients",
                Front[].class
        );

        return Arrays.asList(patients);
    }

    public Front getPatientById(Long id) {
        return restTemplate.getForObject(
                gatewayUrl + "/patients/" + id,
                Front.class
        );
    }

    public void addPatient(Front patient) {
        restTemplate.postForObject(
                gatewayUrl + "/patients",
                patient,
                Front.class
        );
    }

    public void updatePatient(Long id, Front patient) {
        restTemplate.put(
                gatewayUrl + "/patients/" + id,
                patient
        );
    }
}