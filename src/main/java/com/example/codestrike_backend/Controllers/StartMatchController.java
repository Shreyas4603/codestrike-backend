package com.example.codestrike_backend.Controllers;

import com.example.codestrike_backend.Classes.QueueData;
import com.example.codestrike_backend.Services.StartMatchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Controller for handling match start requests.
 *
 * This controller exposes endpoints for adding players to a matchmaking queue
 * and starting a match. It uses DeferredResult to handle asynchronous responses.
 */
@RestController
@RequestMapping("/api/start")
public class StartMatchController {

    // Service layer dependency to manage the matchmaking logic
    @Autowired
    private StartMatchService startMatchService;

    /**
     * Endpoint to add a player to the matchmaking queue and start a match.
     *
     * This method receives player data (QueueData), adds the player to the queue,
     * and asynchronously returns a response indicating the match status.
     *
     * @param body the QueueData containing player details
     * @param request the HTTP request to access additional details (like user info)
     * @return DeferredResult<String> a deferred response that will be populated asynchronously
     */
    @PostMapping("/match")
    public DeferredResult<String> start(@RequestBody QueueData body, HttpServletRequest request) {
        // Add the player to the matchmaking queue and return a deferred result
        return startMatchService.AddPlayerToQueue(request, body);
    }
}
