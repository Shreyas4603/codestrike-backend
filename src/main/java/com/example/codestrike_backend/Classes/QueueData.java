package com.example.codestrike_backend.Classes;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.context.request.async.DeferredResult;

public class QueueData  implements Comparable<QueueData>{
    private String userId;
    private int rating;
    private DeferredResult<String> result;

    public QueueData(String userId, int rating, DeferredResult<String> result) {
        this.userId = userId;
        this.rating = rating;
        this.result = result;
    }

    public QueueData() {
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

    @Override
    public int compareTo(QueueData other) {
        return Integer.compare(other.getRating(), this.getRating());
    }
}
