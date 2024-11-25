package com.example.codestrike_backend.Services;

import com.example.codestrike_backend.Classes.QueueData;
import com.example.codestrike_backend.Clients.NodeClient;
import com.example.codestrike_backend.Handlers.GameWebSocketHandler;
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




    private final MatchMakeHelper matchMakeHelper =new MatchMakeHelper();

    @Autowired
    public QueueProcessingService(
            @Qualifier("NoviceQueue") PriorityBlockingQueue<QueueData> noviceQueue,
            @Qualifier("CoderQueue") PriorityBlockingQueue<QueueData> coderQueue,
            @Qualifier("HackerQueue") PriorityBlockingQueue<QueueData> hackerQueue,
            @Qualifier("GuruQueue") PriorityBlockingQueue<QueueData> guruQueue,
            @Qualifier("MasterQueue") PriorityBlockingQueue<QueueData> masterQueue
    ) {
        this.queues = new PriorityBlockingQueue[]{noviceQueue, coderQueue, hackerQueue, guruQueue, masterQueue};
    }

    @Scheduled(fixedDelay = 2000) // Runs every 2 seconds
    public void processQueues() {
        for (int i = 0; i < queues.length; i++) {
            PriorityBlockingQueue<QueueData> currentQueue = queues[currentQueueIndex];
            currentQueueIndex = (currentQueueIndex + 1) % queues.length; // Update for next round-robin

            if (currentQueue.size() < 2) {
                continue; // Skip queues with fewer than 2 players
            }

            QueueData player1 = currentQueue.poll();
            QueueData player2 = currentQueue.poll();

            // @Todo Uncomment this later
//            if (!isPlayerOnlineOrRequeue(player1, player2, currentQueue)) {
//                continue; // Skip to next queue if a player is offline
//            }

            assert player2 != null;


            matchMakeHelper.createMatch(player1, player2);
            return; // Exit after processing a match
        }

        System.out.println("No matches found in any queue.");
    }


}
