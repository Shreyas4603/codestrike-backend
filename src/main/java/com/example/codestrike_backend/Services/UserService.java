package com.example.codestrike_backend.Services;

import com.example.codestrike_backend.Classes.LoginRequest;
import com.example.codestrike_backend.Models.User;
import com.example.codestrike_backend.Models.UserGameData;
import com.example.codestrike_backend.Repositories.UserGameDataRepository;
import com.example.codestrike_backend.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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

/**
 * Service class for handling user registration and login operations.
 * This class manages user authentication, validation, and creation of user game data.
 */
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

    // Password encoder with strength 12 to securely hash user passwords.
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * Handles user registration. Validates input data, checks for existing users,
     * hashes the user's password, and saves both user and initial game data.
     *
     * @param userRequest The user data to be registered.
     * @return ResponseEntity containing the result of the registration operation.
     */
    public ResponseEntity<Object> register(User userRequest) {
        // Check if any required field (email, username, password) is missing
        if (userRequest.getEmail() == null || userRequest.getEmail().isEmpty() ||
                userRequest.getPassword() == null || userRequest.getPassword().isEmpty() ||
                userRequest.getUsername() == null || userRequest.getUsername().isEmpty()) {
            // Respond with a message indicating the missing fields
            Map<String, String> res = new HashMap<>();
            res.put("data", "'username', 'email' or 'password' not provided");
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }

        // Check if the username already exists in the database
        Optional<User> existingUsername = userRepository.findByUsername(userRequest.getUsername());
        if (existingUsername.isPresent()) {
            Map<String, String> res = new HashMap<>();
            res.put("error", "username already exists");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

        // Check if the email already exists in the database
        Optional<User> existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser.isPresent()) {
            Map<String, String> res = new HashMap<>();
            res.put("error", "user already exists");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

        // Hash the password before storing it in the database for security
        userRequest.setPassword(encoder.encode(userRequest.getPassword()));
        User savedUser = userRepository.save(userRequest);

        // Create initial game data for the newly registered user
        UserGameData initialData = new UserGameData();
        initialData.setUserId(savedUser.getId()); // Set user ID
        initialData.setWins(0); // Initialize wins to 0
        initialData.setLosses(0); // Initialize losses to 0
        initialData.setRating(100); // Default rating set to 100
        initialData.setAttemptedQuestions(new ArrayList<>()); // Optionally, initialize empty list for attempted questions

        // Save the initial game data to the database
        userGameDataRepository.save(initialData);

        // Respond with the saved user data
        Map<Object, Object> res = new HashMap<>();
        res.put("data", savedUser.userData()); // Return user data (excluding sensitive information)
        return new ResponseEntity<>(res, HttpStatus.CREATED); // HTTP 201 for successful creation
    }

    /**
     * Handles user login. Validates the user's credentials and generates a JWT token if successful.
     *
     * @param body The login request containing the user's email and password.
     * @return ResponseEntity containing the login result or error message.
     */
    public ResponseEntity<Object> login(LoginRequest body) {
        Map<String, String> res = new HashMap<>();

        // Check if required fields (email, password) are provided
        if (body.getEmail() == null || body.getEmail().isEmpty() ||
                body.getPassword() == null || body.getPassword().isEmpty()) {
            // Respond with a message indicating missing fields
            res.put("data", "'email' or 'password' not provided");
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN); // HTTP 403 if fields are missing
        }

        // Find the user by email
        Optional<User> userOpt = userRepository.findByEmail(body.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Check if the provided password matches the hashed password in the database
            if (encoder.matches(body.getPassword(), user.getPassword())) {
                // Generate JWT token on successful authentication
                String token = jwtService.generateToken(user.getId());

                // Respond with login success and token
                res.put("data", "Login Successful");
                res.put("token", token);
                res.put("_id", user.getId());
                res.put("username", user.getUsername());
                return new ResponseEntity<>(res, HttpStatus.OK); // HTTP 200 for successful login
            } else {
                // Respond with incorrect password error
                res.put("data", "Invalid Password");
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN); // HTTP 403 for invalid password
            }
        } else {
            // Respond with user not found error
            res.put("data", "User doesn't exist");
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN); // HTTP 403 for non-existent user
        }
    }

    public ResponseEntity<?> profile(HttpServletRequest request){

        String userId=(String) request.getAttribute("userId");

        HashMap <Object,Object> response=new HashMap<>();

        User user=userRepository.findById(userId).get();
        UserGameData userGameData=userGameDataRepository.findByUserId(userId).get();

        response.put("userId",user.getId());
        response.put("username",user.getUsername());
        response.put("wins",userGameData.getWins());
        response.put("losses",userGameData.getLosses());
        response.put("rank",userGameData.getRank());

        return ResponseEntity.ok(response);




    }
}
