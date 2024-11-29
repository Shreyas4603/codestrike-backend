package com.example.codestrike_backend.Filters;

import com.example.codestrike_backend.Models.User;
import com.example.codestrike_backend.Repositories.UserRepository;
import com.example.codestrike_backend.Services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * JWT Filter to handle authentication via JWT tokens.
 * It validates the JWT token and sets authentication in the security context.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;  // Service to handle JWT operations

    @Autowired
    private UserRepository userRepository;  // Repository to fetch user data

    /**
     * Filters the request and authenticates the user based on the JWT token.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during filtering
     * @throws IOException if an error occurs during filtering
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        // Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userId = null;

        // Check if the Authorization header contains a Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // Extract the token from the header
            userId = jwtService.extractId(token);  // Extract the user ID from the token
        }

        // If a user ID is extracted from the token and the user is not already authenticated
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Attempting authentication for user: " + userId);

            // Fetch user data from the repository
            Optional<User> userOpt = userRepository.findByUserId(userId);

            // Validate the token and check if the user exists
            if (userOpt.isPresent() && jwtService.validateToken(token, userOpt.get())) {
                User user = userOpt.get();  // Get the authenticated user

                // Create an authentication object without authorities
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), null);

                // Set the authentication details (user info from the request)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Attach user ID to the request for further processing
                request.setAttribute("userId", userId);

                System.out.println("Successfully authenticated user: " + userId);
            } else {
                // If token validation fails or user is not found
                System.out.println("Token validation failed or user not found.");
            }
        }

        // Continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
