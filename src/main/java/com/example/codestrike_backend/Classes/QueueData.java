package com.example.codestrike_backend.Classes;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents data for a user in the matchmaking queue.
 * This class implements Comparable to allow sorting by user rating in descending order.
 */
public class QueueData implements Comparable<QueueData> {

    // Unique identifier for the user
    private String userId;

    // User's rating used for matchmaking purposes
    private int rating;

    // DeferredResult to handle asynchronous responses
    private DeferredResult<String> result;

    // List of question IDs the user has already attempted
    private ArrayList<String> attemptedQuestions;

    // The timestamp when the user joined the queue
    private LocalDateTime startTime;

    /**
     * Parameterized constructor.
     * Initializes all fields with the provided values.
     *
     * @param userId            the unique identifier for the user
     * @param rating            the user's rating
     * @param result            the deferred result for asynchronous processing
     * @param attemptedQuestions a list of attempted question IDs
     * @param startTime         the time the user joined the queue
     */
    public QueueData(String userId, int rating, DeferredResult<String> result, ArrayList<String> attemptedQuestions, LocalDateTime startTime) {
        this.userId = userId;
        this.rating = rating;
        this.result = result;
        this.attemptedQuestions = (attemptedQuestions != null) ? attemptedQuestions : new ArrayList<>();
        this.startTime = startTime;
    }

    /**
     * Default constructor.
     * Initializes attemptedQuestions to avoid null pointer exceptions.
     */
    public QueueData() {
        this.attemptedQuestions = new ArrayList<>();
    }

    // Getters and Setters

    /**
     * @return the user's unique identifier
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId sets the user's unique identifier
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the user's rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating sets the user's rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @return the deferred result for asynchronous processing
     */
    public DeferredResult<String> getResult() {
        return result;
    }

    /**
     * @param result sets the deferred result for asynchronous processing
     */
    public void setResult(DeferredResult<String> result) {
        this.result = result;
    }

    /**
     * @return the list of question IDs the user has attempted
     */
    public ArrayList<String> getAttemptedQuestions() {
        return attemptedQuestions;
    }

    /**
     * @param attemptedQuestions sets the list of attempted question IDs
     */
    public void setAttemptedQuestions(ArrayList<String> attemptedQuestions) {
        this.attemptedQuestions = (attemptedQuestions != null) ? attemptedQuestions : new ArrayList<>();
    }

    /**
     * @return the timestamp when the user joined the queue
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime sets the timestamp when the user joined the queue
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Compares this QueueData object to another by their ratings in descending order.
     *
     * @param other the other QueueData object to compare against
     * @return a negative integer, zero, or a positive integer as this object's rating is
     * less than, equal to, or greater than the other object's rating
     */
    @Override
    public int compareTo(@NotNull QueueData other) {
        return Integer.compare(other.getRating(), this.getRating());
    }
}
