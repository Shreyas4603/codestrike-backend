package com.example.codestrike_backend.Services;

import com.example.codestrike_backend.Classes.QueueData;
import com.example.codestrike_backend.Handlers.GameWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.concurrent.PriorityBlockingQueue;


@Service
public class QueueProcessingService {

    private final PriorityBlockingQueue<QueueData> noviceQueue;
    private final PriorityBlockingQueue<QueueData> coderQueue;
    private final PriorityBlockingQueue<QueueData> hackerQueue;
    private final PriorityBlockingQueue<QueueData> guruQueue;
    private final PriorityBlockingQueue<QueueData> masterQueue;


    @Autowired
    private  GameWebSocketHandler gameWebSocketHandler;



    private final PriorityBlockingQueue<QueueData>[] queues; // Array for round-robin access
    private int currentQueue = 0; // Index for round-robin access

    @Autowired
    public QueueProcessingService(@Qualifier("NoviceQueue") PriorityBlockingQueue<QueueData> noviceQueue,
                                  @Qualifier("CoderQueue") PriorityBlockingQueue<QueueData> coderQueue,
                                  @Qualifier("HackerQueue") PriorityBlockingQueue<QueueData> hackerQueue,
                                  @Qualifier("GuruQueue") PriorityBlockingQueue<QueueData> guruQueue,
                                  @Qualifier("MasterQueue") PriorityBlockingQueue<QueueData> masterQueue
                                  ) {
        this.noviceQueue = noviceQueue;
        this.coderQueue = coderQueue;
        this.hackerQueue = hackerQueue;
        this.guruQueue = guruQueue;
        this.masterQueue = masterQueue;

        // Add queues to an array for easier round-robin handling
        this.queues = new PriorityBlockingQueue[]{noviceQueue, coderQueue, hackerQueue, guruQueue, masterQueue};
    }

    @Scheduled(fixedDelay = 2000) // Runs every 2 seconds
    public void processQueues() throws InterruptedException {


        // Round-robin through the queues
        for (int i = 0; i < queues.length; i++) {
            Thread.sleep(2000);

            PriorityBlockingQueue<QueueData> current = queues[currentQueue]; // Get the current queue

//            System.out.println("Checking : "+(currentQueue+1));
//            System.out.println("Data in current queue : "+current.toString());
//            System.out.println("Player socket info "+gameWebSocketHandler.playerSessions);

            if (current.size() > 1) { // Check if at least 2 players are in the queue
                QueueData player1 = current.poll(); // Get the first player
                QueueData player2 = current.poll(); // Get the second player


                if (!gameWebSocketHandler.isPlayerOnline(player1.getUserId())) {
                    // Player1 is disconnected, remove from queue and notify of cancellation
                    player1.getResult().setResult("Match cancelled due to player disconnect 1");
                    current.remove(player1);
                    current.add(player2); // Return player2 back to queue if present
                    return; // Exit without attempting further match
                }

                if (!gameWebSocketHandler.isPlayerOnline(player2.getUserId())) {
                    // Player2 is disconnected, remove from queue and notify of cancellation
                    player2.getResult().setResult("Match cancelled due to player disconnect 2");
                    current.remove(player2);
                    current.add(player1); // Return player1 back to queue
                    return; // Exit without attempting further match
                }


                String matchData = "Match found between " + player1.getUserId() + " and " + player2.getUserId();


                // Set the result for both players
                player1.getResult().setResult(matchData);
                player2.getResult().setResult(matchData);


                // Log the match (optional)
                System.out.println(matchData);

                // After processing the match, move to the next queue in round-robin fashion
                currentQueue = (currentQueue + 1) % queues.length;
                return; // Exit the method after finding a match
            }

            // Move to the next queue in round-robin fashion
            currentQueue = (currentQueue + 1) % queues.length;
        }

        // If no matches were found in any of the queues
        System.out.println("All queues are empty.");
    }
}
