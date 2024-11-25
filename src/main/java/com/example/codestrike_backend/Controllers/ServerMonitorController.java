package com.example.codestrike_backend.Controllers;

import com.example.codestrike_backend.Classes.ServerMetrics;
import com.example.codestrike_backend.Services.ServerMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class ServerMonitorController {

    @Autowired
    private ServerMetricsService serverMetricsService;

    @GetMapping("")
    public ResponseEntity<String> checkStatus() {
        return new ResponseEntity<>("Server is running at port 8080", HttpStatus.OK);
    }

    @GetMapping("/metrics")
    public ResponseEntity<ServerMetrics> getMetrics() {
        ServerMetrics metrics = serverMetricsService.getMetrics();
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }
}
