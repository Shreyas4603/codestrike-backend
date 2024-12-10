package com.example.codestrike_backend.Handlers;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class MatchWebSocketHandler extends TextWebSocketHandler {

    // Store WebSocket sessions by matchId. Each matchId will map to a Set of sessions.
    private final ConcurrentHashMap<String, Set<WebSocketSession>> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extract matchId from the URL path
        String path = session.getUri().getPath();
        UriTemplate template = new UriTemplate("/ws/match/{matchId}");
        String matchId = template.match(path).get("matchId");

        // Get or create the set of sessions for the matchId
        sessions.computeIfAbsent(matchId, k -> new CopyOnWriteArraySet<>()).add(session);

        session.sendMessage(new TextMessage("Connected to match: " + matchId));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        // Find the matchId and remove the session from the set
        String path = session.getUri().getPath();
        UriTemplate template = new UriTemplate("/ws/match/{matchId}");
        String matchId = template.match(path).get("matchId");

        Set<WebSocketSession> sessionSet = sessions.get(matchId);
        if (sessionSet != null) {
            sessionSet.remove(session);
            // If no more sessions exist for this matchId, remove the matchId entry from the map
            if (sessionSet.isEmpty()) {
                sessions.remove(matchId);
            }
        }
    }

    /**
     * Sends a message to all sessions associated with a specific matchId.
     */


    public void sendMessageToMatch(String matchId, HashMap<String, String> message) {
        Set<WebSocketSession> sessionSet = sessions.get(matchId);
        if (sessionSet != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Convert HashMap to JSON string
                String jsonMessage = objectMapper.writeValueAsString(message);

                for (WebSocketSession session : sessionSet) {
                    if (session.isOpen()) {
                        try {
                            session.sendMessage(new TextMessage(jsonMessage));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
