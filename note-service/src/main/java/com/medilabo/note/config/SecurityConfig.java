package com.medilabo.note.config;

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
     * Configure la sécurité HTTP du note-service.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                /*
                 * Le note-service est une API REST appelée avec HTTP Basic.
                 * Il n'utilise pas de formulaire HTML ni de cookie de session.
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * Toutes les routes du microservice nécessitent
                 * une authentification.
                 */
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )

                /*
                 * Active l'authentification HTTP Basic.
                 */
                .httpBasic(Customizer.withDefaults())

                /*
                 * Le serveur ne conserve aucune session.
                 * Les identifiants doivent être envoyés à chaque requête.
                 */
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    /**
     * Crée le compte technique autorisé à appeler le note-service.
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
     * Fournit l'encodeur utilisé pour stocker le mot de passe
     * du compte technique.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}