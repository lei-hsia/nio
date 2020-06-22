package com.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/lubanws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 定义客户端能向服务端发送消息，客户端需要是什么prefix
        registry.setApplicationDestinationPrefixes("/someprefix");

        // topic: 定义客户端能够回调收到服务端的哪些东西，必须是服务端这边同意可以订阅的,否则客户端可以乱写
        // user:  服务端给客户端发消息，prefix默认是 /user
        registry.enableSimpleBroker("/topic", "/user");
    }
}
