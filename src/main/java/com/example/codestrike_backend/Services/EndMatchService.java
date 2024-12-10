package com.example.codestrike_backend.Services;

import com.example.codestrike_backend.Classes.QueueData;
import com.example.codestrike_backend.Handlers.MatchWebSocketHandler;
import com.example.codestrike_backend.Models.Match;
import com.example.codestrike_backend.Models.UserGameData;
import com.example.codestrike_backend.Repositories.MatchRepository;
import com.example.codestrike_backend.Repositories.UserGameDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EndMatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserGameDataRepository userGameDataRepository;

    @Autowired
    CodeSubmitService codeSubmitService;

    @Autowired
    private MatchWebSocketHandler matchWebSocketHandler;

    // Weights for the rating formula
    private final double w1 = 0.3;  // Weight for Submissions
    private final double w2 = 0.8;  // Weight for Execution Time
    private final double w3 = 0.5;  // Weight for Test Case Performance

    // Min and Max raw rating values for normalization
    private final double minRawRating = 0.0;  // Theoretical minimum raw rating
    private final double maxRawRating = w1 + w2 + w3;  // Theoretical maximum raw rating


    // Helper method to calculate the raw rating
    private double calculateRawRating(int numberOfSubmissions, double executionTime, int testCasesPassed, int totalTestCases,
                                      int maxSubmissions, double bestExecutionTime) {
        // Calculate the Submissions Score
        double submissionsScore = (double) numberOfSubmissions / maxSubmissions;

        // Calculate the Execution Time Score
        double executionTimeScore = bestExecutionTime / executionTime;

        // Calculate the Test Case Performance Score
        double testCaseScore = (double) testCasesPassed / totalTestCases;

        // Calculate the final raw rating using the weighted formula
        return w1 * submissionsScore + w2 * executionTimeScore + w3 * testCaseScore;
    }

    // Helper method to normalize the rating to a range of 20-40 (winner) or 10-20 (loser)
    private double normalizeRating(double rawRating, double minRange, double maxRange) {
        return minRange + (rawRating - minRawRating) * (maxRange - minRange) / (maxRawRating - minRawRating);
    }

    public ResponseEntity<?> endMatch(String matchId, String userId) {



        // Retrieve match details
        Match matchDetails = matchRepository.findById(matchId).orElse(null);
        //if both run out of time and for teh loser
        assert matchDetails != null;
        if((matchDetails.getP1DeltaRating()!=0 && matchDetails.getP2DeltaRating()!=0) ||
                (matchDetails.getPlayer1Passed() < matchDetails.getPlayer1TotalTestCases() &&
                        (matchDetails.getPlayer2Passed()< matchDetails.getPlayer2TotalTestCases()))){
            return ResponseEntity.ok(matchRepository.findById(matchId));
        }



        if (matchDetails == null) {
            return ResponseEntity.badRequest().body("Invalid match ID");
        }

        // Retrieve the winner's rank
        String winnerRank = userGameDataRepository.findByUserId(userId).orElse(null).getRank();
        if (winnerRank == null) {
            return ResponseEntity.badRequest().body("Winner rank not found");
        }

        // Retrieve the loser's rank
        String loserId = (matchDetails.getPlayer1Id().equals(userId)) ? matchDetails.getPlayer2Id() : matchDetails.getPlayer1Id();
        String loserRank = userGameDataRepository.findByUserId(loserId).orElse(null).getRank();
        if (loserRank == null) {
            return ResponseEntity.badRequest().body("Loser rank not found");
        }

        // Determine max submissions and best execution time
        int maxSubmissions = Math.max(matchDetails.getNumberOfPlayer1Submissions(), matchDetails.getNumberOfPlayer2Submissions());
        double bestExecutionTime = Math.min(matchDetails.getP1ExecutionTime(), matchDetails.getP2ExecutionTime());

        // Determine the total number of test cases
        int totalTestCases = matchDetails.getPlayer1TotalTestCases() != 0 ?
                matchDetails.getPlayer1TotalTestCases() :
                matchDetails.getPlayer2TotalTestCases();

        // Variables for the winner's data
        int winnerSubmissions, loserSubmissions;
        double winnerExecutionTime, loserExecutionTime;
        int winnerTestCasesPassed, loserTestCasesPassed;

        // Get the winner's and loser's data
        int winnerType = codeSubmitService.getPlayerType(userId, matchId);

        if (winnerType == 1) {
            winnerSubmissions = matchDetails.getNumberOfPlayer1Submissions();
            winnerExecutionTime = matchDetails.getP1ExecutionTime();
            winnerTestCasesPassed = matchDetails.getPlayer1Passed();

            loserSubmissions = matchDetails.getNumberOfPlayer2Submissions();
            loserExecutionTime = matchDetails.getP2ExecutionTime();
            loserTestCasesPassed = matchDetails.getPlayer2Passed();
        } else if (winnerType == 2) {
            winnerSubmissions = matchDetails.getNumberOfPlayer2Submissions();
            winnerExecutionTime = matchDetails.getP2ExecutionTime();
            winnerTestCasesPassed = matchDetails.getPlayer2Passed();

            loserSubmissions = matchDetails.getNumberOfPlayer1Submissions();
            loserExecutionTime = matchDetails.getP1ExecutionTime();
            loserTestCasesPassed = matchDetails.getPlayer1Passed();
        } else {
            return ResponseEntity.badRequest().body("Invalid user IDs");
        }

        // Calculate the raw ratings
        double winnerRawRating = calculateRawRating(winnerSubmissions, winnerExecutionTime, winnerTestCasesPassed, totalTestCases,
                maxSubmissions, bestExecutionTime);

        double loserRawRating = calculateRawRating(loserSubmissions, loserExecutionTime, loserTestCasesPassed, totalTestCases,
                maxSubmissions, bestExecutionTime);

        // Normalize the ratings
        double winnerRating = normalizeRating(winnerRawRating, 20, 40); // Winner's rating in range 20-40
        double loserRating = normalizeRating(loserRawRating, 10, 20);   // Loser's rating in range 10-20

        // Adjust ratings based on rank
        double winnerMultiplier = getRankMultiplier(winnerRank);
        double loserMultiplier = getRankMultiplier(loserRank);

        winnerRating *= winnerMultiplier;
        loserRating *= loserMultiplier;

        // Ensure ratings are clamped within their respective ranges
        winnerRating = Math.min(Math.max(winnerRating, 20), 40);
        loserRating = Math.min(Math.max(loserRating, 10), 20);


        if(winnerType==1){
            matchDetails.setP1DeltaRating((int)winnerRating);
            matchDetails.setP2DeltaRating(-(int)loserRating);

            UserGameData userGameData=userGameDataRepository.findByUserId(matchDetails.getPlayer1Id()).get();
            int wins=userGameData.getWins();
            int rating=userGameData.getRating();

            //Setting for player 1
            userGameData.setWins(wins+1);
            userGameData.setRating(rating+(int)winnerRating);
            userGameData.setRank(getPlayerNewRank(rating+(int)winnerRating));
            userGameDataRepository.save(userGameData);//Saving the changes


            //Setting for p2
            userGameData=userGameDataRepository.findByUserId(matchDetails.getPlayer2Id()).get();
            int loses=userGameData.getLosses();
            rating=userGameData.getRating()-(int)loserRating; //new rating

            userGameData.setLosses(loses+1);
            userGameData.setRating(rating);
            userGameData.setRank(getPlayerNewRank(rating));

            userGameDataRepository.save(userGameData);

        }
         else if (winnerType==2) {
            matchDetails.setP2DeltaRating((int)winnerRating);
            matchDetails.setP1DeltaRating(-(int)loserRating);


            UserGameData userGameData=userGameDataRepository.findByUserId(matchDetails.getPlayer2Id()).get();
            int wins=userGameData.getWins();
            int rating=userGameData.getRating();

            //Setting for player 1
            userGameData.setWins(wins+1);
            userGameData.setRating(rating+(int)winnerRating);
            userGameData.setRank(getPlayerNewRank(rating+(int)winnerRating));
            userGameDataRepository.save(userGameData);//Saving the changes


            //Setting for p2
            userGameData=userGameDataRepository.findByUserId(matchDetails.getPlayer1Id()).get();
            int loses=userGameData.getLosses();
            rating=userGameData.getRating()-(int)loserRating; //new rating

            userGameData.setLosses(loses+1);
            userGameData.setRating(rating);
            userGameData.setRank(getPlayerNewRank(rating));

            userGameDataRepository.save(userGameData);
        }

         HashMap<String ,String> socketMessage=new HashMap<>();
         socketMessage.put("message","MATCH_END");
         socketMessage.put("userId",userId);
         matchWebSocketHandler.sendMessageToMatch(matchId,socketMessage);
         matchRepository.save(matchDetails);

        // Return the result
        return ResponseEntity.ok(matchRepository.findById(matchId));
//        return ResponseEntity.ok("Winner rating: " + winnerRating + ", Loser rating: " + loserRating);
    }

    // Helper method to get the rank multiplier
    private double getRankMultiplier(String rank) {
        switch (rank.toLowerCase()) {
            case "novice":
                return 1.2;
            case "coder":
                return 1.1;
            case "hacker":
                return 1.0;
            case "guru":
                return 0.9;
            case "master":
                return 0.8;
            default:
                return 1.0; // Default multiplier for unknown ranks
        }
    }
    private String getPlayerNewRank(int  rating) {


        // Add player to the corresponding queue based on their rating
        if (rating >= 0 && rating <= 200) {
            return "novice";
        } else if (rating >= 201 && rating <= 400) {
            return "coder";
        } else if (rating >= 401 && rating <= 600) {
            return "hacker";
        } else if (rating >= 601 && rating <= 800) {
            return "guru";
        } else if (rating >= 800) {
            return "master";
        } else {
            throw new IllegalArgumentException("Invalid user rating: " + rating);
        }
    }


}

