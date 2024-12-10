package com.example.codestrike_backend.Classes;

import java.time.LocalDateTime;

public class MatchProblemDetails {
    private String matchId;
    private String problemId;
    private String problemStatement;
    private String player1Name;
    private int player1Rating;
    private String player1Rank;
    private String player2Name;
    private int player2Rating;
    private String player2Rank;
    private String solutionTemplate;
    private LocalDateTime startTime;

    public MatchProblemDetails() {
    }

    public MatchProblemDetails(String matchId, String problemId, String problemStatement,
                               String player1Name, int player1Rating, String player1Rank, String player2Name,
                               int player2Rating, String player2Rank, String solutionTemplate, LocalDateTime startTime) {
        this.matchId = matchId;
        this.problemId = problemId;
        this.problemStatement = problemStatement;
        this.player1Name = player1Name;
        this.player1Rating = player1Rating;
        this.player1Rank = player1Rank;
        this.player2Name = player2Name;
        this.player2Rating = player2Rating;
        this.player2Rank = player2Rank;
        this.solutionTemplate = solutionTemplate;
        this.startTime = startTime;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public int getPlayer1Rating() {
        return player1Rating;
    }

    public void setPlayer1Rating(int player1Rating) {
        this.player1Rating = player1Rating;
    }

    public String getPlayer1Rank() {
        return player1Rank;
    }

    public void setPlayer1Rank(String player1Rank) {
        this.player1Rank = player1Rank;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public int getPlayer2Rating() {
        return player2Rating;
    }

    public void setPlayer2Rating(int player2Rating) {
        this.player2Rating = player2Rating;
    }

    public String getPlayer2Rank() {
        return player2Rank;
    }

    public void setPlayer2Rank(String player2Rank) {
        this.player2Rank = player2Rank;
    }

    public String getSolutionTemplate() {
        return solutionTemplate;
    }

    public void setSolutionTemplate(String solutionTemplate) {
        this.solutionTemplate = solutionTemplate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
