package com.example.codestrike_backend.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "match")
public class Match {

    @Field("matchId")
    private String matchId; // Unique identifier for the match
    private String problemId; // Identifier for the problem associated with the match
    private String player1Id; // Identifier for player 1
    private String player2Id; // Identifier for player 2
    private LocalDateTime startTime; // Start time of the match
    private LocalDateTime endTime; // End time of the match

    @CreatedDate // Automatically populate when the document is created
    private Instant createdAt;

    @LastModifiedDate // Automatically update when the document is modified
    private Instant modifiedAt;

    // Constructors
    public Match() {
        this.matchId = UUID.randomUUID().toString(); // Ensure matchId is generated on creation
    }

    public Match(String matchId, String problemId, String player1Id, String player2Id, LocalDateTime startTime, LocalDateTime endTime) {
        this.matchId = (matchId == null) ? UUID.randomUUID().toString() : matchId; // Generate UUID if null
        this.problemId = problemId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    // @CreatedDate and @LastModifiedDate are handled automatically by Spring Data MongoDB

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    // toString method
    @Override
    public String toString() {
        return "Match{" +
                "matchId='" + matchId + '\'' +
                ", problemId='" + problemId + '\'' +
                ", player1Id='" + player1Id + '\'' +
                ", player2Id='" + player2Id + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
