package com.example.codestrike_backend.Helpers;

import com.example.codestrike_backend.Classes.QueueData;
import com.example.codestrike_backend.Clients.NodeClient;
import com.example.codestrike_backend.Handlers.GameWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.PriorityBlockingQueue;

public class MatchMakeHelper {

    @Autowired
    private GameWebSocketHandler gameWebSocketHandler;

    @Autowired
    private NodeClient nodeClient;

    public int getAverageRating(QueueData player1, QueueData player2){
        return (player1.getRating()+player2.getRating())/2;
    }

    public boolean isPlayerOnlineOrRequeue(QueueData player1, QueueData player2, PriorityBlockingQueue<QueueData> queue) {
        if (!gameWebSocketHandler.isPlayerOnline(player1.getUserId())) {
            player1.getResult().setResult("Match cancelled due to player 1 disconnect");
            queue.offer(player2); // Requeue player2
            return false;
        }
        if (!gameWebSocketHandler.isPlayerOnline(player2.getUserId())) {
            player2.getResult().setResult("Match cancelled due to player 2 disconnect");
            queue.offer(player1); // Requeue player1
            return false;
        }
        return true;
    }

    public void createMatch(QueueData player1, QueueData player2) {
        String matchData = "Match found between " + player1.getUserId() + " and " + player2.getUserId();
        player1.getResult().setResult(matchData);
        player2.getResult().setResult(matchData);

        System.out.println(matchData); // Log match info
    }


}
