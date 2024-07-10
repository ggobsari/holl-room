package com.hollroom.chatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompBrokerConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버에 메시지가 도착하면 해당 경로를 구독하는 클라이언트에게 메시지를 보냄
        registry.enableSimpleBroker("/sub/chat");
        // 클라이언트가 서버로 메시지를 보낼때 사용하는 경로의 접두사
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹소켓stomp이 지원되지 않는 브라우저에서도 사용할 수 있도록 withSockJS 설정
        registry.addEndpoint("/websocket/start").withSockJS();
    }
}
