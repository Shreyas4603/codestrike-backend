package com.example.codestrike_backend.Services;

import com.example.codestrike_backend.Classes.QueueData;
import com.example.codestrike_backend.Helpers.MatchMakeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.PriorityBlockingQueue;

@Service
public class QueueProcessingService {

    private final PriorityBlockingQueue<QueueData>[] queues; // Array for round-robin access
    private int currentQueueIndex = 0; // Index for round-robin access

    @Autowired
    private MatchMakeHelper matchMakeHelper;

    /**
     * Constructor to initialize the queues with different levels
     *
     * @param noviceQueue Priority queue for novice players
     * @param coderQueue  Priority queue for coder players
     * @param hackerQueue Priority queue for hacker players
     * @param guruQueue   Priority queue for guru players
     * @param masterQueue Priority queue for master players
     */
    @Autowired
    public QueueProcessingService(
            @Qualifier("NoviceQueue") PriorityBlockingQueue<QueueData> noviceQueue,
            @Qualifier("CoderQueue") PriorityBlockingQueue<QueueData> coderQueue,
            @Qualifier("HackerQueue") PriorityBlockingQueue<QueueData> hackerQueue,
            @Qualifier("GuruQueue") PriorityBlockingQueue<QueueData> guruQueue,
            @Qualifier("MasterQueue") PriorityBlockingQueue<QueueData> masterQueue
    ) {
        // Initialize the queues array for round-robin access
        this.queues = new PriorityBlockingQueue[]{noviceQueue, coderQueue, hackerQueue, guruQueue, masterQueue};
    }

    /**
     * Scheduled task to process the queues every 2 seconds.
     * Matches players from each queue if there are at least two players in the queue.
     */
    @Scheduled(fixedDelay = 2000) // Runs every 2 seconds
    public void processQueues() {
        // Iterate over each queue in round-robin fashion
        for (int i = 0; i < queues.length; i++) {
            PriorityBlockingQueue<QueueData> currentQueue = queues[currentQueueIndex];
            currentQueueIndex = (currentQueueIndex + 1) % queues.length; // Update for next round-robin

            // If the current queue has fewer than 2 players, skip to the next one
            if (currentQueue.size() < 2) {
//                System.out.println("Skipped queue: " + i + " (Not enough players)");
                continue;
            }

            // Poll two players from the current queue
            QueueData player1 = currentQueue.poll();
            QueueData player2 = currentQueue.poll();

            // Ensure both players are non-null
            assert player1 != null && player2 != null;

            // Uncomment the next line if player availability checks are required
            // if (!isPlayerOnlineOrRequeue(player1, player2, currentQueue)) { continue; }

            // Use the matchMakeHelper to create a match
            matchMakeHelper.createMatch(player1, player2);
            return; // Exit after processing one match
        }

        // If no matches were found in any queue, log that information
//        System.out.println("No matches found in any queue.");
    }
}
