package com.akdev.devconnect.devconnect.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class UserInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("Inside preSend method of UserInterceptor");
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String userId = accessor.getFirstNativeHeader("user-id"); // custom header
            accessor.setUser(new UsernamePasswordAuthenticationToken(userId, null));
            System.out.println("User ID set in the header: " + userId);
        }

        return message;
    }
}
