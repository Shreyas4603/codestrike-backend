package com.example.codestrike_backend.Config;

import com.example.codestrike_backend.Handlers.GameWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class for WebSocket support.
 *
 * This class configures WebSocket handling for the game service, enabling
 * communication between the server and clients in real-time. It sets up a WebSocket
 * handler to manage WebSocket connections and interactions.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    // GameWebSocketHandler to handle WebSocket connections and interactions
    private final GameWebSocketHandler gameWebSocketHandler;

    /**
     * Constructor-based dependency injection for GameWebSocketHandler.
     *
     * @param gameWebSocketHandler the handler for managing WebSocket interactions
     */
    public WebSocketConfig(GameWebSocketHandler gameWebSocketHandler) {
        this.gameWebSocketHandler = gameWebSocketHandler;
    }

    /**
     * Registers the WebSocket handler to handle WebSocket connections.
     *
     * - Configures the endpoint "/ws" for WebSocket communication.
     * - Allows all origins to connect to this WebSocket endpoint.
     *
     * @param registry the WebSocketHandlerRegistry used to register WebSocket handlers
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Register the GameWebSocketHandler to handle requests at the "/ws" endpoint
        registry.addHandler(gameWebSocketHandler, "/ws")
                .setAllowedOrigins("*"); // Allow connections from any origin (can be restricted for security)
    }
}
