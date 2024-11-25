package com.example.codestrike_backend.Classes;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;

public class QueueData implements Comparable<QueueData> {
    private String userId;
    private int rating;
    private DeferredResult<String> result;
    private ArrayList<String> attemptedQuestions; // New field for attempted questions

    public QueueData(String userId, int rating, DeferredResult<String> result, ArrayList<String> attemptedQuestions) {
        this.userId = userId;
        this.rating = rating;
        this.result = result;
        this.attemptedQuestions = attemptedQuestions;
    }

    public QueueData() {
        this.attemptedQuestions = new ArrayList<>(); // Initialize to avoid null
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public DeferredResult<String> getResult() {
        return result;
    }

    public void setResult(DeferredResult<String> result) {
        this.result = result;
    }

    public List<String> getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public void setAttemptedQuestions(ArrayList<String> attemptedQuestions) {
        this.attemptedQuestions = attemptedQuestions;
    }

    @Override
    public int compareTo(@NotNull QueueData other) {
        return Integer.compare(other.getRating(), this.getRating());
    }
}
