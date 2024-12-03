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
    private LocalDateTime player1StartTime; // Start time for player 1
    private LocalDateTime player2StartTime; // Start time for player 2
    private LocalDateTime player1EndTime; // End time for player 1
    private LocalDateTime player2EndTime; // End time for player 2

    @CreatedDate // Automatically populate when the document is created
    private Instant createdAt;

    @LastModifiedDate // Automatically update when the document is modified
    private Instant modifiedAt;

    // Constructors
    public Match() {
        this.matchId = UUID.randomUUID().toString(); // Ensure matchId is generated on creation
    }

    public Match(String matchId, String problemId, String player1Id, String player2Id,
                 LocalDateTime player1StartTime, LocalDateTime player1EndTime,
                 LocalDateTime player2StartTime, LocalDateTime player2EndTime) {
        this.matchId = (matchId == null) ? UUID.randomUUID().toString() : matchId; // Generate UUID if null
        this.problemId = problemId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1StartTime = player1StartTime;
        this.player1EndTime = player1EndTime;
        this.player2StartTime = player2StartTime;
        this.player2EndTime = player2EndTime;
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

    // toString method
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
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
