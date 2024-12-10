package com.example.codestrike_backend.Services;

import com.example.codestrike_backend.Classes.QueueData;
import com.example.codestrike_backend.Models.UserGameData;
import com.example.codestrike_backend.Repositories.UserGameDataRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class StartMatchService {

    private final PriorityBlockingQueue<QueueData> noviceQueue;
    private final PriorityBlockingQueue<QueueData> coderQueue;
    private final PriorityBlockingQueue<QueueData> hackerQueue;
    private final PriorityBlockingQueue<QueueData> guruQueue;
    private final PriorityBlockingQueue<QueueData> masterQueue;

    @Autowired
    private UserGameDataRepository userGameDataRepository;

    @Autowired
    public StartMatchService(
            @Qualifier("NoviceQueue") PriorityBlockingQueue<QueueData> noviceQueue,
            @Qualifier("CoderQueue") PriorityBlockingQueue<QueueData> coderQueue,
            @Qualifier("HackerQueue") PriorityBlockingQueue<QueueData> hackerQueue,
            @Qualifier("GuruQueue") PriorityBlockingQueue<QueueData> guruQueue,
            @Qualifier("MasterQueue") PriorityBlockingQueue<QueueData> masterQueue) {
        this.noviceQueue = noviceQueue;
        this.coderQueue = coderQueue;
        this.hackerQueue = hackerQueue;
        this.guruQueue = guruQueue;
        this.masterQueue = masterQueue;
    }

    /**
     * Add a player to the appropriate queue based on their rating.
     *
     * @param request the HTTP request containing user information
     * @param body    the QueueData containing player details
     * @return DeferredResult to handle async responses
     */
    public DeferredResult<String> AddPlayerToQueue(HttpServletRequest request, QueueData body) {
        String userId = (String) request.getAttribute("userId");

        // Retrieve user game data from the database
        UserGameData userGameData = getUserGameData(userId);

        // Create DeferredResult for async processing
        DeferredResult<String> result = new DeferredResult<>(null);

        // Create QueueData for the player
        QueueData queueData = new QueueData(userId, userGameData.getRating(), result,
                userGameData.getAttemptedQuestions(), body.getStartTime(),userId);

        // Add the player to the respective queue based on their rating
        assignPlayerToQueue(queueData);

        return result;  // Return the DeferredResult for async processing
    }

    /**
     * Retrieves the user's game data. Throws an exception if not found.
     *
     * @param userId the ID of the user
     * @return the UserGameData for the user
     */
    private UserGameData getUserGameData(String userId) {
        Optional<UserGameData> userGameDataOptional = userGameDataRepository.findByUserId(userId);
        if (userGameDataOptional.isPresent()) {
            return userGameDataOptional.get();
        } else {
            throw new RuntimeException("UserGameData not found for userId: " + userId);
        }
    }

    /**
     * Assign the player to the appropriate queue based on their rating.
     *
     * @param queueData the data of the player to be added
     */
    private void assignPlayerToQueue(QueueData queueData) {
        int rating = queueData.getRating();

        // Add player to the corresponding queue based on their rating
        if (rating >= 0 && rating <= 200) {
            noviceQueue.add(queueData);
        } else if (rating >= 201 && rating <= 400) {
            coderQueue.add(queueData);
        } else if (rating >= 401 && rating <= 600) {
            hackerQueue.add(queueData);
        } else if (rating >= 601 && rating <= 800) {
            guruQueue.add(queueData);
        } else if (rating >= 800) {
            masterQueue.add(queueData);
        } else {
            throw new IllegalArgumentException("Invalid user rating: " + rating);
        }
    }
}
