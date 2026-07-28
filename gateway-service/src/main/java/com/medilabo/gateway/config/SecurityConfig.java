package com.medilabo.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

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
     * Protège toutes les routes exposées par la Gateway.
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        return http
                /*
                 * La Gateway expose uniquement des API protégées
                 * avec HTTP Basic et n'utilise pas de formulaire HTML.
                 */
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                /*
                 * Toutes les requêtes doivent être authentifiées.
                 */
                .authorizeExchange(exchange -> exchange
                        .anyExchange().authenticated()
                )

                /*
                 * Active HTTP Basic.
                 */
                .httpBasic(Customizer.withDefaults())

                /*
                 * Désactive la page de connexion automatique.
                 */
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

                /*
                 * Désactive la déconnexion basée sur une session.
                 */
                .logout(ServerHttpSecurity.LogoutSpec::disable)

                .build();
    }

    /**
     * Crée le compte technique utilisé pour accéder à la Gateway.
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        UserDetails serviceUser = User.builder()
                .username(serviceUsername)
                .password(passwordEncoder.encode(servicePassword))
                .roles("SERVICE")
                .build();

        return new MapReactiveUserDetailsService(serviceUser);
    }

    /**
     * Encode le mot de passe du compte technique.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}