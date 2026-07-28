package com.medilabo.patient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final String serviceUsername;
    private final String servicePassword;

    public SecurityConfig(
            @Value("${security.service.username}") String serviceUsername,
            @Value("${security.service.password}") String servicePassword) {

        this.serviceUsername = serviceUsername;
        this.servicePassword = servicePassword;
    }

    /**
     * Définit les règles de sécurité HTTP du patient-service.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                /*
                 * Le patient-service est une API REST utilisant HTTP Basic.
                 * Il n'utilise ni formulaire HTML ni cookie de session.
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * Toutes les requêtes doivent être authentifiées.
                 */
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )

                /*
                 * Active l'authentification HTTP Basic.
                 */
                .httpBasic(Customizer.withDefaults())

                /*
                 * Aucune session serveur n'est créée.
                 * Les identifiants doivent être présents à chaque requête.
                 */
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    /**
     * Crée le compte technique autorisé à appeler le patient-service.
     */
    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        UserDetails serviceUser = User.builder()
                .username(serviceUsername)
                .password(passwordEncoder.encode(servicePassword))
                .roles("SERVICE")
                .build();

        return new InMemoryUserDetailsManager(serviceUser);
    }

    /**
     * Encode le mot de passe utilisé par Spring Security.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}