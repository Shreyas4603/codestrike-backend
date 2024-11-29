package com.example.codestrike_backend.Config;

import com.example.codestrike_backend.Filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
/**
 * Configuration class for Spring Security.
 *
 * This class defines security configurations, including authentication, authorization,
 * and session management. It incorporates JWT-based authentication to secure API endpoints
 * and ensures stateless session handling.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Configures the security filter chain for handling HTTP requests.
     *
     * - Disables CSRF protection since JWT is used for securing API endpoints.
     * - Configures endpoint-specific access permissions.
     * - Enables stateless session management to avoid storing user sessions on the server.
     * - Adds a custom JWT filter before the default username-password authentication filter.
     *
     * @param http the HttpSecurity object for configuring web security
     * @return the configured SecurityFilterChain
     * @throws Exception if any configuration error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF as JWT is stateless
                .authorizeHttpRequests(requests -> requests
                        // Public endpoints that do not require authentication
                        .requestMatchers(
                                "api/users/login",
                                "api/users/register",
                                "/metrics",
                                "/api/start/match",
                                "/",
                                "/api/problem/**"
                        ).permitAll()
                        .requestMatchers("/ws/**").permitAll() // Allow WebSocket endpoints
                        .anyRequest().authenticated() // Protect all other endpoints
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless session management
                )
                // Add the custom JWT filter to validate tokens before authentication
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Exposes the AuthenticationManager bean, which is required for authentication processes.
     *
     * - The `AuthenticationManager` is automatically configured by Spring Security.
     * - This bean is primarily used for programmatically managing authentication.
     *
     * @param config the AuthenticationConfiguration for accessing the manager
     * @return the configured AuthenticationManager instance
     * @throws Exception if any configuration error occurs
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
