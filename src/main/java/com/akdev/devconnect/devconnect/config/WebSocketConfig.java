package com.akdev.devconnect.devconnect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Broker for client subscriptions
        //config.setApplicationDestinationPrefixes("/app"); // Prefix for client messages
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*")
                .withSockJS(); // WebSocket endpoint
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        System.out.println("Configuring client inbound channel");
        registration.interceptors(new UserInterceptor());
        System.out.println("Client inbound channel configured");
    }
}
