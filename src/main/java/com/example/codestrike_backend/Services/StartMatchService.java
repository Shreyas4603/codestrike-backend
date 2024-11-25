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
    public StartMatchService(@Qualifier("NoviceQueue") PriorityBlockingQueue<QueueData> noviceQueue,
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

    public DeferredResult<String> AddPlayerToQueue(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        int rating;
        ArrayList<String> attemptedQuestions;
        Optional<UserGameData> userGameDataOptional = userGameDataRepository.findByUserId(userId);

        if (userGameDataOptional.isPresent()) {
            rating = userGameDataOptional.get().getRating();
            attemptedQuestions=userGameDataOptional.get().getAttemptedQuestions();

            DeferredResult<String> result = new DeferredResult<>(null);
            QueueData queueData = new QueueData(userId, rating ,result,attemptedQuestions);

            // Add to the respective queues based on rating
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
                throw new RuntimeException("Fault in User rating");
            }

            return result; // Return the deferred result to be processed later
        } else {
            throw new RuntimeException("UserGameData not found for userId: " + userId);
        }
    }
}
