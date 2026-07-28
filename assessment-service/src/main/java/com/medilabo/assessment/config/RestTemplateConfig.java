package com.medilabo.assessment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private final String clientUsername;
    private final String clientPassword;

    public RestTemplateConfig(
            @Value("${security.client.username}") String clientUsername,
            @Value("${security.client.password}") String clientPassword) {

        this.clientUsername = clientUsername;
        this.clientPassword = clientPassword;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .basicAuthentication(
                        clientUsername,
                        clientPassword
                )
                .build();
    }
}