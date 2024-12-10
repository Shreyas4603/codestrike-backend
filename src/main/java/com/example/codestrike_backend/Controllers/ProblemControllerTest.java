package com.example.codestrike_backend.Controllers;

import com.example.codestrike_backend.Repositories.MatchRepository;
import com.example.codestrike_backend.Services.ProblemServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling problem-related endpoints.
 *
 * This controller exposes endpoints related to problem services, such as retrieving
 * unique problem data. It interacts with the ProblemServiceTest to fetch required data.
 */
@RestController
@RequestMapping("/api/problem")
public class ProblemControllerTest {

    // Service layer dependency injected to handle problem-related business logic
    @Autowired
    private ProblemServiceTest problemServiceTest;

    @Autowired
    private MatchRepository matchRepository;
    /**
     * Endpoint to retrieve a unique problem question.
     *
     * This endpoint is responsible for fetching a unique problem question by invoking
     * the ProblemServiceTest to fetch the necessary data.
     *
     * @return a string containing the unique problem question
     */
    @GetMapping("/unique")
    public String getUniqueQuestion() {
        // Logging for debugging purposes
        System.out.println("In here");

        // Call the service layer to get the unique problem question
        String res = problemServiceTest.getUniqueQuestion();

        // Return the unique problem question response
        return res;
    }

    @GetMapping("")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok(matchRepository.findAll());
    }
}
