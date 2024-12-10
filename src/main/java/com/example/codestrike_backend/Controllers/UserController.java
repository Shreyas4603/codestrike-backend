package com.example.codestrike_backend.Controllers;

import com.example.codestrike_backend.Classes.LoginRequest;
import com.example.codestrike_backend.Models.User;
import com.example.codestrike_backend.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users") // Use a more RESTful path
@CrossOrigin(origins = "*") // Allows all origins for now, but should be restricted to specific origins in production
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.encoder = new BCryptPasswordEncoder(12); // Initialize encoder here for better dependency management
    }

    /**
     * Get the CSRF token for the current session.
     *
     * @param request the HttpServletRequest to get the CSRF token
     * @return the CSRF token
     */
    @GetMapping("/token")
    public CsrfToken getToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    /**
     * Register a new user.
     *
     * @param userRequest the user data for registration
     * @return ResponseEntity with the status of the registration
     */
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User userRequest) {
        return userService.register(userRequest);
    }

    /**
     * Login an existing user.
     *
     * @param body the login credentials
     * @return ResponseEntity with the status of the login
     */
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest body) {
        return userService.login(body);
    }
}
