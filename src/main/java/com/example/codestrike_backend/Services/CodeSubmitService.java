package com.example.codestrike_backend.Services;
import com.example.codestrike_backend.Handlers.MatchWebSocketHandler;
import com.example.codestrike_backend.Models.User;
import com.example.codestrike_backend.Models.UserGameData;
import com.example.codestrike_backend.Repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.codestrike_backend.Classes.CodeSubmission;
import com.example.codestrike_backend.Classes.TestResult;
import com.example.codestrike_backend.Clients.CompilerClient;
import com.example.codestrike_backend.Models.Match;
import com.example.codestrike_backend.Repositories.MatchRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CodeSubmitService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CompilerClient compilerClient;

    @Autowired
    private MatchWebSocketHandler matchWebSocketHandler;

    @Autowired
    UserRepository userRepository;

    /**
     * Determines the player type based on userId and matchId.
     *
     * @param userId  The user's ID.
     * @param match_Id The match's ID.
     * @return 1 if Player 1, 2 if Player 2, -1 if no match found or userId is invalid.
     */
    public int getPlayerType(String userId, String match_Id) {
        Optional<Match> optionalMatch = matchRepository.findById(match_Id);
        if (optionalMatch.isEmpty()) {
            return -1; // Match not found
        }

        Match match = optionalMatch.get();
        if (userId.equalsIgnoreCase(match.getPlayer1Id())) {
            return 1; // Player 1
        } else if (userId.equalsIgnoreCase(match.getPlayer2Id())) {
            return 2; // Player 2
        } else {
            return -1; // User ID doesn't match either player
        }
    }

    /**
     * Handles code compilation logic and updates submission counts.
     *
     * @param body   The code submission details.
     * @param userId The user's ID.
     * @return A response indicating the result of the compilation process.
     */
    public ResponseEntity<String> compileCode(CodeSubmission body, String userId) {

        String match_Id = body.getMatchId();
        int playerType = getPlayerType(userId, match_Id);

        System.out.println("user id "+playerType);

        // Return error if matchId or userId is invalid
        if (playerType == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid matchId or userId");
        }

        // Fetch match details or return error if not found
        Optional<Match> matchOptional = matchRepository.findById(match_Id);
        if (matchOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Match not found with ID: " + match_Id);
        }

        Match matchDetails = matchOptional.get();

//        // Update the submission count based on player type
//        if (playerType == 1) {
//            matchDetails.setNumberOfPlayer1Submissions(matchDetails.getNumberOfPlayer1Submissions() + 1);
//        } else if (playerType == 2) {
//            matchDetails.setNumberOfPlayer2Submissions(matchDetails.getNumberOfPlayer2Submissions() + 1);
//        }

        // Prepare the code submission for the compiler client
        body.setLanguage("python");
        body.setProblemId(matchDetails.getProblemId());

        // Call the compiler client to compile the code
        String compileResult = compilerClient.compileCode(body);
        HashMap<String, String> messageData = new HashMap<>(); // Initialize the map

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TestResult result = objectMapper.readValue(compileResult, TestResult.class);

            if (playerType == 1) {
                matchDetails.setNumberOfPlayer1Submissions(matchDetails.getNumberOfPlayer1Submissions() + 1);
                matchDetails.setP1ExecutionTime(result.getExecution_time_ms());
                matchDetails.setPlayer1TotalTestCases(result.getTotalTestcases());
                matchDetails.setPlayer1Passed(result.getPassed());
                matchDetails.setPlayer1Failed(result.getFailed());

                // Construct message and send it
                messageData = constructSocketMessage(
                        matchDetails.getPlayer1Id(),
                        matchDetails.getPlayer1TotalTestCases(),
                        matchDetails.getPlayer1Passed()
                );
                matchWebSocketHandler.sendMessageToMatch(matchDetails.get_id(), messageData);
            } else if (playerType == 2) {
                matchDetails.setNumberOfPlayer2Submissions(matchDetails.getNumberOfPlayer2Submissions() + 1);
                matchDetails.setP2ExecutionTime(result.getExecution_time_ms());
                matchDetails.setPlayer2TotalTestCases(result.getTotalTestcases());
                matchDetails.setPlayer2Passed(result.getPassed());
                matchDetails.setPlayer2Failed(result.getFailed());

                // Construct message and send it
                messageData = constructSocketMessage(
                        matchDetails.getPlayer2Id(),
                        matchDetails.getPlayer2TotalTestCases(),
                        matchDetails.getPlayer2Passed()
                );
                matchWebSocketHandler.sendMessageToMatch(matchDetails.get_id(), messageData);
            }

            // Optional: Remove this redundant call if unnecessary
            // matchWebSocketHandler.sendMessageToMatch(matchOptional.get().getMatchId(), messageData);

        } catch (Exception e) {
            System.err.println("Error while processing result: " + e.getMessage());
            e.printStackTrace();
        }




        matchRepository.save(matchDetails);

        // Return the result from the compiler
        return ResponseEntity.ok(compileResult);
    }

    public HashMap<String, String> constructSocketMessage(String userId, int totalTestCases, int passed ) {

        HashMap<String, String> messageData=new HashMap<>();

        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with ID: " + userId));
        String message = String.format("%s passed %d / %d", user.getUsername(), passed, totalTestCases);
        System.out.println("Websocket message : "+message);

        messageData.put("message",message);
        messageData.put("userId",userId);
        return messageData;
    }

}
