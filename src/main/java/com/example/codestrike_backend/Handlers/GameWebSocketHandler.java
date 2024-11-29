package com.example.codestrike_backend.Handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles WebSocket connections and player sessions.
 * This component manages players' connections and keeps track of their active sessions.
 */
@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    // Store WebSocket sessions by player ID
    private final ConcurrentHashMap<String, WebSocketSession> playerSessions = new ConcurrentHashMap<>();

    /**
     * Called after a WebSocket connection is established.
     * Stores the player session in the map.
     *
     * @param session the WebSocket session
     */
    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        String playerId = getPlayerIdFromSession(session);
        if (playerId != null) {
            playerSessions.put(playerId, session);
            System.out.println("Player " + playerId + " connected.");
            printPlayerSessions();
        }
    }

    /**
     * Called after a WebSocket connection is closed.
     * Removes the player session from the map.
     *
     * @param session the WebSocket session
     * @param status the close status of the connection
     */
    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
        String playerId = getPlayerIdFromSession(session);
        if (playerId != null) {
            playerSessions.remove(playerId);
            System.out.println("Player " + playerId + " disconnected.");
            printPlayerSessions();
        }
    }

    /**
     * Extracts the player ID from the WebSocket session URI.
     * The player ID is expected to be passed as a query parameter in the URI.
     *
     * @param session the WebSocket session
     * @return the extracted player ID, or null if extraction fails
     */
    private String getPlayerIdFromSession(WebSocketSession session) {
        try {
            // Extract player ID from the URI query parameter (e.g., "?playerId=123")
            return Objects.requireNonNull(session.getUri()).getQuery().split("=")[1];
        } catch (Exception e) {
            System.out.println("Failed to extract player ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if the player is currently online.
     *
     * @param playerId the player ID to check
     * @return true if the player is online, false otherwise
     */
    public boolean isPlayerOnline(String playerId) {
        return playerSessions.containsKey(playerId);
    }

    /**
     * Returns the map of player sessions.
     *
     * @return the map of player ID to WebSocket session
     */
    public ConcurrentHashMap<String, WebSocketSession> getPlayerSessions() {
        return playerSessions;
    }

    /**
     * Prints the current state of player sessions (for debugging).
     */
    private void printPlayerSessions() {
        System.out.println("Current active player sessions: " + playerSessions);
    }
}
