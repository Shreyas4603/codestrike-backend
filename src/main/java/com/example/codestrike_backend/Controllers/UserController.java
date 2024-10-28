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
@CrossOrigin(origins = "*") // Allows all origins

public class UserController {

    @Autowired
    private UserService userService;



    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @GetMapping("/token")
    public CsrfToken getToken(HttpServletRequest request) {
//        System.out.println("in here"+(CsrfToken) request.getAttribute("_csrf"));
        return (CsrfToken) request.getAttribute("_csrf");
    }

    // Endpoint to create a new user
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User userRequest) {
        return userService.register(userRequest);

    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest body) {
        return userService.login(body);
    }


}
