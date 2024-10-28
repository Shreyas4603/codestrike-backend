package com.example.codestrike_backend.Services;


import com.example.codestrike_backend.Classes.LoginRequest;
import com.example.codestrike_backend.Models.User;
import com.example.codestrike_backend.Models.UserGameData;
import com.example.codestrike_backend.Repositories.UserGameDataRepository;
import com.example.codestrike_backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGameDataRepository userGameDataRepository;





    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public ResponseEntity<Object> register(User userRequest) {
        Optional<User> existingUser = userRepository.findByEmail(userRequest.getEmail());
        Optional<User> existingUsername = userRepository.findByUsername(userRequest.getUsername());

        if (userRequest.getEmail() == null || userRequest.getEmail().isEmpty() ||
                userRequest.getPassword() == null || userRequest.getPassword().isEmpty() ||
                userRequest.getUsername() == null || userRequest.getUsername().isEmpty()) {
            Map<String, String> res = new HashMap<>();
            res.put("data", "'username', 'email' or 'password' not provided");
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }

        if (existingUsername.isPresent()) {
            Map<String, String> res = new HashMap<>();
            res.put("error", "username already exists");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

        if (existingUser.isPresent()) {
            Map<String, String> res = new HashMap<>();
            res.put("error", "user already exists");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

        userRequest.setPassword(encoder.encode(userRequest.getPassword()));
        User savedUser = userRepository.save(userRequest);

        //create the user game data info

        UserGameData initialData = new UserGameData();
        initialData.setUserId(savedUser.getUserId()); // Set userId based on the saved user
        initialData.setWins(0); // Set default wins
        initialData.setLosses(0); // Set default losses
        initialData.setRating(100); // Default rating to 100

        // Optionally, initialize the attemptedQuestions list
        initialData.setAttemptedQuestions(new ArrayList<>());

// Save UserGameData to the database
        userGameDataRepository.save(initialData);

        Map<Object, Object> res = new HashMap<>();
        res.put("data", savedUser.userData());

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    public ResponseEntity<Object> login(LoginRequest body) {
        Optional<User> userOpt = userRepository.findByEmail(body.getEmail());
        Map<String, String> res = new HashMap<>();

        if (body.getEmail() == null || body.getEmail().isEmpty() ||
                body.getPassword() == null || body.getPassword().isEmpty()) {
            res.put("data", "'email' or 'password' not provided");
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (encoder.matches(body.getPassword(), user.getPassword())) {
                // Generate Token
                String token = jwtService.generateToken(user.getUserId());

                res.put("data", "Login Successful");
                res.put("token", token);

                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                res.put("data", "Invalid Password");
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
            }
        } else {
            res.put("data", "User doesn't exist");
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }
    }


}
