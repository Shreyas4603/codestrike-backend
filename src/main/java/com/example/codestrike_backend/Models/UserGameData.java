package com.example.codestrike_backend.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Document(collection = "UserGameData")
public class UserGameData {


    @Id
    private String _id;


    @Field("gameDataID")
    private String gameDataID;
    private String userId;
    private int wins = 0; // Default wins to 0
    private int rating = 100; // Default rating to 100
    private int losses = 0; // Default losses to 0
    private String rank="novice";
    private ArrayList<String> attemptedQuestions = new ArrayList<>(); // Initialize the list directly

    public UserGameData() {
        this.gameDataID = UUID.randomUUID().toString();
    }

    public UserGameData(String userId, int wins, int rating, int losses,String rank,String _id) {
        this.gameDataID = UUID.randomUUID().toString();
        this.userId = userId;
        this.wins = wins;
        this.rating = rating == 0 ? 100 : rating; // Default rating to 100 if it's 0
        this.losses = losses;
        this.rank=rank;
        this._id=_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    // Getters and Setters
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

    public ArrayList<String> getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public void setAttemptedQuestions(ArrayList<String> attemptedQuestions) {
        this.attemptedQuestions = attemptedQuestions;
    }

    public void addAttemptedQuestion(String questionId) {
        this.attemptedQuestions.add(questionId); // No need for null check anymore
    }

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant modifiedAt;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    // toString method (optional)
    @Override
    public String toString() {
        return "UserGameData{" +
                "gameDataID='" + gameDataID + '\'' +
                ", userId='" + userId + '\'' +
                ", wins=" + wins +
                ", rating=" + rating +
                ", losses=" + losses +
                ", attemptedQuestions=" + attemptedQuestions +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
