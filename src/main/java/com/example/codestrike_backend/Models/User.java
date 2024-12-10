package com.example.codestrike_backend.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Users")
public class User {

    @Id
    private String _id;

    @Field("userId") // Explicitly map userId
    private String userId;
    private String email;
    private String password;
    private String username;

    @CreatedDate // Automatically populate when the document is created
    private Instant createdAt;

    @LastModifiedDate // Automatically update when the document is modified
    private Instant modifiedAt;

    // Default constructor
    public User() {
        this.userId = UUID.randomUUID().toString();
    }

    // Constructor with parameters, including _id
    public User(String _id, String userId, String email, String password, String username) {
        this._id = _id;
        this.userId = (userId == null) ? UUID.randomUUID().toString() : userId; // Generate UUID if null
        this.email = email;
        this.password = password;
        this.username = username;
    }

    // Getters and Setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    // userData method
    public Map<String, Object> userData() {
        return Map.of(
                "id", this._id,
                "email", this.email
        );
    }

    // toString method (optional)
    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
