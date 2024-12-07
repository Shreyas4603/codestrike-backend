package com.example.codestrike_backend.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "match")
public class Match {


    @Id
    private String _id;
    @Field("matchId")
    private String matchId; // Unique identifier for the match
    private String problemId; // Identifier for the problem associated with the match
    private String player1Id; // Identifier for player 1
    private String player2Id; // Identifier for player 2
    private LocalDateTime player1StartTime; // Start time for player 1
    private LocalDateTime player2StartTime; // Start time for player 2
    private LocalDateTime player1EndTime; // End time for player 1
    private LocalDateTime player2EndTime; // End time for player 2
    private int numberOfPlayer1Submissions; // Count of submissions by player 1
    private int numberOfPlayer2Submissions; // Count of submissions by player 2
    private double p1ExecutionTime= Double.MAX_VALUE;;
    private double p2ExecutionTime= Double.MAX_VALUE;;

    // Player test case results
    private int player1TotalTestCases;
    private int player1Passed;
    private int player1Failed;

    private int player2TotalTestCases;
    private int player2Passed;
    private int player2Failed;

    @CreatedDate // Automatically populate when the document is created
    private Instant createdAt;

    @LastModifiedDate // Automatically update when the document is modified
    private Instant modifiedAt;

    // Constructors
    public Match() {
        this.matchId = UUID.randomUUID().toString(); // Ensure matchId is generated on creation
    }

    // Constructor for setting all values
    public Match(String matchId, String _id,String problemId, String player1Id, String player2Id,
                 LocalDateTime player1StartTime, LocalDateTime player1EndTime,
                 LocalDateTime player2StartTime, LocalDateTime player2EndTime,
                 int numberOfPlayer1Submissions, int numberOfPlayer2Submissions,
                 double p1ExecutionTime, double p2ExecutionTime,
                 int player1TotalTestCases, int player1Passed, int player1Failed,
                 int player2TotalTestCases, int player2Passed, int player2Failed) {
        this._id=_id;
        this.matchId = matchId;
        this.problemId = problemId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1StartTime = player1StartTime;
        this.player1EndTime = player1EndTime;
        this.player2StartTime = player2StartTime;
        this.player2EndTime = player2EndTime;
        this.numberOfPlayer1Submissions = numberOfPlayer1Submissions;
        this.numberOfPlayer2Submissions = numberOfPlayer2Submissions;
        this.p1ExecutionTime = p1ExecutionTime;
        this.p2ExecutionTime = p2ExecutionTime;
        this.player1TotalTestCases = player1TotalTestCases;
        this.player1Passed = player1Passed;
        this.player1Failed = player1Failed;
        this.player2TotalTestCases = player2TotalTestCases;
        this.player2Passed = player2Passed;
        this.player2Failed = player2Failed;
    }

    // Getters and Setters for all fields


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(String player1Id) {
        this.player1Id = player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(String player2Id) {
        this.player2Id = player2Id;
    }

    public LocalDateTime getPlayer1StartTime() {
        return player1StartTime;
    }

    public void setPlayer1StartTime(LocalDateTime player1StartTime) {
        this.player1StartTime = player1StartTime;
    }

    public LocalDateTime getPlayer2StartTime() {
        return player2StartTime;
    }

    public void setPlayer2StartTime(LocalDateTime player2StartTime) {
        this.player2StartTime = player2StartTime;
    }

    public LocalDateTime getPlayer1EndTime() {
        return player1EndTime;
    }

    public void setPlayer1EndTime(LocalDateTime player1EndTime) {
        this.player1EndTime = player1EndTime;
    }

    public LocalDateTime getPlayer2EndTime() {
        return player2EndTime;
    }

    public void setPlayer2EndTime(LocalDateTime player2EndTime) {
        this.player2EndTime = player2EndTime;
    }

    public int getNumberOfPlayer1Submissions() {
        return numberOfPlayer1Submissions;
    }

    public void setNumberOfPlayer1Submissions(int numberOfPlayer1Submissions) {
        this.numberOfPlayer1Submissions = numberOfPlayer1Submissions;
    }

    public int getNumberOfPlayer2Submissions() {
        return numberOfPlayer2Submissions;
    }

    public void setNumberOfPlayer2Submissions(int numberOfPlayer2Submissions) {
        this.numberOfPlayer2Submissions = numberOfPlayer2Submissions;
    }

    public double getP1ExecutionTime() {
        return p1ExecutionTime;
    }

    public void setP1ExecutionTime(double p1ExecutionTime) {
        this.p1ExecutionTime = p1ExecutionTime;
    }

    public double getP2ExecutionTime() {
        return p2ExecutionTime;
    }

    public void setP2ExecutionTime(double p2ExecutionTime) {
        this.p2ExecutionTime = p2ExecutionTime;
    }

    public int getPlayer1TotalTestCases() {
        return player1TotalTestCases;
    }

    public void setPlayer1TotalTestCases(int player1TotalTestCases) {
        this.player1TotalTestCases = player1TotalTestCases;
    }

    public int getPlayer1Passed() {
        return player1Passed;
    }

    public void setPlayer1Passed(int player1Passed) {
        this.player1Passed = player1Passed;
    }

    public int getPlayer1Failed() {
        return player1Failed;
    }

    public void setPlayer1Failed(int player1Failed) {
        this.player1Failed = player1Failed;
    }



    public int getPlayer2TotalTestCases() {
        return player2TotalTestCases;
    }

    public void setPlayer2TotalTestCases(int player2TotalTestCases) {
        this.player2TotalTestCases = player2TotalTestCases;
    }

    public int getPlayer2Passed() {
        return player2Passed;
    }

    public void setPlayer2Passed(int player2Passed) {
        this.player2Passed = player2Passed;
    }

    public int getPlayer2Failed() {
        return player2Failed;
    }

    public void setPlayer2Failed(int player2Failed) {
        this.player2Failed = player2Failed;
    }



    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId='" + matchId + '\'' +
                ", problemId='" + problemId + '\'' +
                ", player1Id='" + player1Id + '\'' +
                ", player2Id='" + player2Id + '\'' +
                ", player1StartTime=" + player1StartTime +
                ", player1EndTime=" + player1EndTime +
                ", player2StartTime=" + player2StartTime +
                ", player2EndTime=" + player2EndTime +
                ", numberOfPlayer1Submissions=" + numberOfPlayer1Submissions +
                ", numberOfPlayer2Submissions=" + numberOfPlayer2Submissions +
                ", p1ExecutionTime=" + p1ExecutionTime +
                ", p2ExecutionTime=" + p2ExecutionTime +
                ", player1TotalTestCases=" + player1TotalTestCases +
                ", player1Passed=" + player1Passed +
                ", player1Failed=" + player1Failed +
                ", player2TotalTestCases=" + player2TotalTestCases +
                ", player2Passed=" + player2Passed +
                ", player2Failed=" + player2Failed +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
