package com.example.codestrike_backend.Models;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Document(collection = "UserGameData")
public class UserGameData {

    @Field("gameDataID")
    private String gameDataID;
    private String userId;
    private int wins;
    private int rating;
    private int losses;
    private ArrayList<String> attemptedQuestions;

    public ArrayList<String> getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public void setAttemptedQuestions(ArrayList<String> attemptedQuestions) {
        this.attemptedQuestions = attemptedQuestions;
    }

    public void addAttemptedQuestion(String questionId) {
        if (this.attemptedQuestions == null) {
            this.attemptedQuestions = new ArrayList<>(); // Initialize the list if it's null
        }
        this.attemptedQuestions.add(questionId); // Append the new question
    }


    public UserGameData() {
        this.gameDataID = UUID.randomUUID().toString();
    }

    public UserGameData(String gameDataID, String userId, int wins, int rating, int losses) {
        this.gameDataID = gameDataID;
        this.userId = userId;
        this.wins = wins; // Default wins to 0
        this.rating = (rating == 0) ? 100 : rating; // Default rating to 100 if it's 0
        this.losses = losses; // Default losses to 0
    }

    public String getGameDataID() {
        return gameDataID;
    }

    public void setGameDataID(String gameDataID) {
        this.gameDataID = gameDataID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }


    @CreatedDate // Automatically populate when the document is created
    private Instant createdAt;

    @LastModifiedDate // Automatically update when the document is modified
    private Instant modifiedAt;


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
}
