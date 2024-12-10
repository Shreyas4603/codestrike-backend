package com.example.codestrike_backend.Controllers;

import com.example.codestrike_backend.Classes.ServerMetrics;
import com.example.codestrike_backend.Services.ServerMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for monitoring server status and fetching server metrics.
 *
 * This controller exposes endpoints to check the server's operational status and to retrieve
 * system metrics such as memory usage and CPU load.
 */
@RestController
@RequestMapping("")
public class ServerMonitorController {

    // Service layer dependency for fetching server metrics
    @Autowired
    private ServerMetricsService serverMetricsService;

    /**
     * Endpoint to check the server status.
     *
     * This endpoint returns a simple message indicating that the server is running
     * and listening on port 8080. It's typically used for basic health checks.
     *
     * @return a ResponseEntity containing a status message and HTTP OK status
     */
    @GetMapping("")
    public ResponseEntity<String> checkStatus() {
        // Simple message indicating server is running
        return new ResponseEntity<>("Server is running at port 8080", HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve server metrics.
     *
     * This endpoint fetches real-time metrics related to server performance, including
     * memory usage and CPU load, from the ServerMetricsService.
     *
     * @return a ResponseEntity containing the server metrics and HTTP OK status
     */
    @GetMapping("/metrics")
    public ResponseEntity<ServerMetrics> getMetrics() {
        // Fetching metrics via the service layer
        ServerMetrics metrics = serverMetricsService.getMetrics();
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }
}
