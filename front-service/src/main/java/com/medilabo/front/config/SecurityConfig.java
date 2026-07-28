package com.medilabo.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Définit les règles de sécurité de l'application.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize

                        // La page de connexion et les ressources statiques
                        // sont accessibles sans authentification.
                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        // Toutes les autres pages nécessitent
                        // un utilisateur authentifié.
                        .anyRequest().authenticated()
                )

                // Configuration de la connexion par formulaire.
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/patients", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // Configuration de la déconnexion.
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    /**
     * Définit un utilisateur stocké en mémoire.
     *
     * Cet utilisateur est recréé à chaque démarrage
     * de l'application.
     */
    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Encode le mot de passe avant son stockage.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}