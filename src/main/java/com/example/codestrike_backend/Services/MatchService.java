package com.example.codestrike_backend.Services;


import com.example.codestrike_backend.Classes.MatchProblemDetails;
import com.example.codestrike_backend.Classes.ProblemDTO;
import com.example.codestrike_backend.Clients.NodeClient;
import com.example.codestrike_backend.Models.Match;
import com.example.codestrike_backend.Models.User;
import com.example.codestrike_backend.Models.UserGameData;
import com.example.codestrike_backend.Repositories.MatchRepository;
import com.example.codestrike_backend.Repositories.UserGameDataRepository;
import com.example.codestrike_backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    @Autowired
    private NodeClient nodeClient;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserGameDataRepository userGameDataRepository;


    //@todo get problem details - Done
    //@todo get player1 details - Done
    //@todo get player2 details - Done
    //@todo Construst MatchDetails object to send - Done



    public  MatchProblemDetails getMatchInfo(String id){ //it's the match id

        Match matchDetails=matchRepository.findByMatchId(id).get();

        //Problem details
        ProblemDTO problemDetails= nodeClient.getProblemDetails(matchDetails.getProblemId()).getBody();

        //User details
        User player1=userRepository.findByUserId(matchDetails.getPlayer1Id()).get();
        User player2=userRepository.findByUserId(matchDetails.getPlayer2Id()).get();

        //UserGameData
        UserGameData player1GameData=userGameDataRepository.findByUserId(matchDetails.getPlayer1Id()).get();
        UserGameData player2GameData=userGameDataRepository.findByUserId(matchDetails.getPlayer2Id()).get();

        //MatchDetails object

        MatchProblemDetails matchProblemDetails = new MatchProblemDetails();
        matchProblemDetails.setMatchId(id);
        matchProblemDetails.setProblemId(problemDetails.getProblemId());
        matchProblemDetails.setProblemStatement(problemDetails.getProblemStatement());
        matchProblemDetails.setPlayer1Name(player1.getUsername());
        matchProblemDetails.setPlayer1Rank(player1GameData.getRank());
        matchProblemDetails.setPlayer1Rating(player1GameData.getRating());
        matchProblemDetails.setPlayer2Name(player2.getUsername());
        matchProblemDetails.setPlayer2Rank(player2GameData.getRank());
        matchProblemDetails.setPlayer2Rating(player2GameData.getRating());
        matchProblemDetails.setSolutionTemplate(problemDetails.getTemplateCodePy());
        matchProblemDetails.setStartTime(matchDetails.getPlayer1StartTime());


        return matchProblemDetails;
    }
}
