package com.example.codestrike_backend.Helpers;

import com.example.codestrike_backend.Classes.QueueData;
import com.example.codestrike_backend.Classes.UniqueProblemId;
import com.example.codestrike_backend.Clients.NodeClient;
import com.example.codestrike_backend.Handlers.GameWebSocketHandler;
import com.example.codestrike_backend.Models.Match;
import com.example.codestrike_backend.Models.UserGameData;
import com.example.codestrike_backend.Repositories.MatchRepository;
import com.example.codestrike_backend.Repositories.UserGameDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;

@Component
public class MatchMakeHelper {

    private static final Logger log = LoggerFactory.getLogger(MatchMakeHelper.class);

    // Time duration for the match in minutes
    private final int timeToFinish = 30;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private GameWebSocketHandler gameWebSocketHandler;

    @Autowired
    private UserGameDataRepository userGameDataRepository;

    @Autowired
    private NodeClient nodeClient;

    /**
     * Calculates the average rating of two players.
     *
     * @param player1 The first player.
     * @param player2 The second player.
     * @return The average rating of the two players.
     */
    public int getAverageRating(QueueData player1, QueueData player2) {
        return (player1.getRating() + player2.getRating()) / 2;
    }

    /**
     * Determines the rank based on the user's rating.
     *
     * @param rating The user's rating.
     * @return The rank as a string (e.g., "Novice", "Coder", etc.).
     * @throws IllegalArgumentException if the rating is invalid.
     */
    public String getRank(int rating) {
        if (rating < 0) {
            throw new IllegalArgumentException("Fault in User rating: Rating cannot be negative");
        }

        // Determine rank based on rating ranges
        if (rating <= 200) return "novice";
        if (rating <= 400) return "coder";
        if (rating <= 600) return "hacker";
        if (rating <= 800) return "guru";
        return "master"; // For ratings > 800
    }

    /**
     * Checks if both players are online, and if not, requeues the offline player.
     *
     * @param player1 The first player.
     * @param player2 The second player.
     * @param queue   The queue to re-add the offline player to.
     * @return true if both players are online, false otherwise.
     */
    public boolean isPlayerOnlineOrRequeue(QueueData player1, QueueData player2, PriorityBlockingQueue<QueueData> queue) {
        boolean isPlayer1Online = gameWebSocketHandler.isPlayerOnline(player1.getUserId());
        boolean isPlayer2Online = gameWebSocketHandler.isPlayerOnline(player2.getUserId());

        if (!isPlayer1Online) {
            player1.getResult().setResult("Match cancelled due to player 1 disconnect");
            queue.offer(player2); // Requeue player2
            log.info("Player 1 disconnected, requeuing Player 2.");
        }

        if (!isPlayer2Online) {
            player2.getResult().setResult("Match cancelled due to player 2 disconnect");
            queue.offer(player1); // Requeue player1
            log.info("Player 2 disconnected, requeuing Player 1.");
        }

        return isPlayer1Online && isPlayer2Online;
    }

    /**
     * Creates a match between two players.
     *
     * @param player1 The first player.
     * @param player2 The second player.
     */
    public void createMatch(QueueData player1, QueueData player2) {
        // Collect attempted problem IDs for both players
        ArrayList<String> p1AttemptedProblemIds = player1.getAttemptedQuestions();
        ArrayList<String> p2AttemptedProblemIds = player2.getAttemptedQuestions();

        // Prepare the request body for generating a unique problem ID
        UniqueProblemId reqBodyObject = new UniqueProblemId(
                getRank(getAverageRating(player1, player2)),
                p1AttemptedProblemIds,
                p2AttemptedProblemIds
        );

        try {
            // Fetch the unique problem ID from the Node client
            String uniqueProblemId = Objects.requireNonNull(nodeClient.getUnique(reqBodyObject).getBody()).getUniqueProblemId();
            log.info("Unique Problem ID generated: {}", uniqueProblemId);

            // Create match entity to save in the repository
            LocalDateTime player1StartTime = player1.getStartTime();
            LocalDateTime player2StartTime = player2.getStartTime();

            LocalDateTime player1EndTime = player1StartTime.plusMinutes(timeToFinish);
            LocalDateTime player2EndTime = player2StartTime.plusMinutes(timeToFinish);

            System.out.println("P1 "+player1.get_id());
            System.out.println("P2 "+player2.get_id());

            //Add the problem to attempted questions
            UserGameData p1GameData=userGameDataRepository.findByUserId(player1.get_id()).get();
            UserGameData p2GameData=userGameDataRepository.findByUserId(player2.get_id()).get();




            p1GameData.addAttemptedQuestion(uniqueProblemId);
            p2GameData.addAttemptedQuestion(uniqueProblemId);

            userGameDataRepository.save(p1GameData);
            userGameDataRepository.save(p2GameData);



            // Save match details
            Match matchObj = new Match();
            matchObj.setPlayer1Id(player1.getUserId());
            matchObj.setPlayer2Id(player2.getUserId());
            matchObj.setPlayer1StartTime(player1StartTime);
            matchObj.setPlayer2StartTime(player2StartTime);
            matchObj.setPlayer1EndTime(player1EndTime);
            matchObj.setPlayer2EndTime(player2EndTime);
            matchObj.setProblemId(uniqueProblemId);
            matchRepository.save(matchObj);

            // Set match data for both players
            player1.getResult().setResult(matchObj.getMatchId());
            player2.getResult().setResult(matchObj.getMatchId());

            System.out.println(matchObj.toString());

        } catch (Exception e) {
            // Log error and notify players
            log.error("Error creating match for players {} and {}: {}", player1.getUserId(), player2.getUserId(), e.getMessage());
            player1.getResult().setResult("Match creation failed.");
            player2.getResult().setResult("Match creation failed.");
        }
    }
}
