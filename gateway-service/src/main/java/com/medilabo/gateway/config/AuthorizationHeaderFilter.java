package com.medilabo.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;

@Configuration
public class AuthorizationHeaderFilter {

    /**
     * Conserve explicitement l'en-tête Authorization
     * lors du transfert de la requête vers les microservices.
     */
    @Bean
    public GlobalFilter preserveAuthorizationHeader() {
        return (exchange, chain) -> {

            String authorizationHeader = exchange
                    .getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null
                    || authorizationHeader.isBlank()) {

                return chain.filter(exchange);
            }

            var request = exchange
                    .getRequest()
                    .mutate()
                    .headers(headers -> headers.set(
                            HttpHeaders.AUTHORIZATION,
                            authorizationHeader
                    ))
                    .build();

            var updatedExchange = exchange
                    .mutate()
                    .request(request)
                    .build();

            return chain.filter(updatedExchange);
        };
    }
}