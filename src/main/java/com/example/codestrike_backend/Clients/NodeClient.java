package com.example.codestrike_backend.Clients;

import com.example.codestrike_backend.Classes.UniqueProblemId;
import com.example.codestrike_backend.Classes.UniqueProblemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client interface for communicating with the Node.js service.
 *
 * This interface abstracts the interaction with the external Node.js service,
 * providing methods for retrieving unique problem details and sending data.
 * The Feign client simplifies HTTP communication by automatically handling
 * REST API calls.
 */
@FeignClient(name = "node-service", url = "${node-service.url}")
public interface NodeClient {

    /**
     * Sends a GET request to the Node.js service's unique problem endpoint.
     *
     * @return a generic ResponseEntity containing the response from the Node.js service
     */
    @GetMapping("/api/problem/unique")
    ResponseEntity<Object> greet();

    /**
     * Sends a POST request to the Node.js service to retrieve a unique problem.
     *
     * @param request the payload containing details for identifying the unique problem
     * @return a ResponseEntity containing a UniqueProblemResponse from the Node.js service
     */
    @PostMapping("/api/problem/unique")
    ResponseEntity<UniqueProblemResponse> getUnique(@RequestBody UniqueProblemId request);
}
