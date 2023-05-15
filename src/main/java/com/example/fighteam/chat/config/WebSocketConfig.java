package com.example.fighteam.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author : 김효준
 * @fileName : StompWebSocketConfig
 * @since : 2023/04/26
 */

/**
 * TODO-LIST:EndPoint:/ws/chatting 설정 ->view page에서 사용
 *           /queue : 1:1 Chat, /topic : N:N Chat -> 현재 프로젝트는 1:1 채팅만 필요함으로 queue로 Message parsing함
 */
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chatting")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/queue","/topic");
    }
}
