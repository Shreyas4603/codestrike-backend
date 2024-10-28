package com.example.codestrike_backend.Handlers;

import org.jetbrains.annotations.NotNull;

import org.springframework.stereotype.Component;

import org.springframework.web.socket.CloseStatus;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    public final ConcurrentHashMap<String, WebSocketSession> playerSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        String playerId = getPlayerIdFromSession(session);
        if (playerId != null) {
            playerSessions.put(playerId, session);
            System.out.println("Player " + playerId + " connected.");
            System.out.println("data after Connected " + playerSessions);
        }
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
        String playerId = getPlayerIdFromSession(session);
        if (playerId != null) {
            playerSessions.remove(playerId);
            System.out.println("Player " + playerId + " disconnected.");
            System.out.println("data after dc " + playerSessions);
        }
    }

    private String getPlayerIdFromSession(WebSocketSession session) {
        try {
            return Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]; // Replace with your player ID extraction logic
        } catch (Exception e) {
            System.out.println("Failed to extract player ID: " + e.getMessage());
            return null;
        }
    }

    public boolean isPlayerOnline(String playerId) {
        return playerSessions.containsKey(playerId);
    }

    public ConcurrentHashMap<String, WebSocketSession> getPlayerSessions() {
        return playerSessions;
    }
}
