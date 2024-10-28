package com.example.codestrike_backend.Models;



import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;

@Document(collection = "Users")
public class User {
 // MongoDB default ObjectId

    @Field("userId") // Explicitly map userId
    private String userId;
    private String email;
    private String password;
    private String username;

    @CreatedDate // Automatically populate when the document is created
    private Instant createdAt;

    @LastModifiedDate // Automatically update when the document is modified
    private Instant modifiedAt;

    public User() {
        this.userId = UUID.randomUUID().toString();
    }

    public User(String userId, String email, String password, String username) {
        this.userId = (userId == null) ? UUID.randomUUID().toString() : userId; // Generate UUID if null
        this.email = email;
        this.password = password;
        this.username = username;
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

    @EventListener(BeforeSaveEvent.class)
    public void generateUUID() {
        if (this.userId == null) {
            this.userId = UUID.randomUUID().toString();
        }
    }

    public Object userData() {
        HashMap<Object, Object> data = new HashMap<>();
        data.put("id", this.userId);
        data.put("email", this.email);
        return data;
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



}
