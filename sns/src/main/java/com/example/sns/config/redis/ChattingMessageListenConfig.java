package com.example.sns.config.redis;

import com.example.sns.modules.chatting.application.messaging.SubscriberStompDoor;
import com.example.sns.modules.chatting.application.messaging.SubscriberStompMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class ChattingMessageListenConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    RedisMessageListenerContainer container(
        @Qualifier("chattingConnectionFactory") RedisConnectionFactory connectionFactory
        , @Qualifier("stompMessageAdapter") MessageListenerAdapter stompMessageAdapter
        , @Qualifier("stompDoorAdapter") MessageListenerAdapter stompDoorAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // subscribe (redis로 부터)
        container.addMessageListener(stompMessageAdapter, new PatternTopic("MchatRoom:*"));
        container.addMessageListener(stompDoorAdapter, new PatternTopic("DchatRoom:*"));
        return container;
    }

    @Bean(name = "stompMessageAdapter")
    MessageListenerAdapter stompMessageListener(SubscriberStompMessage subscriberStompMessage) {
        // redis에게 메시지 받았을때 실행되는 효과
        return new MessageListenerAdapter(subscriberStompMessage, "onMessage");
    }
    @Bean(name = "stompDoorAdapter")
    MessageListenerAdapter stompDoorListener(SubscriberStompDoor subscriberStompDoor) {
        // redis에게 메시지 받았을때 실행되는 효과
        return new MessageListenerAdapter(subscriberStompDoor, "onMessage");
    }

    @Bean(name = "stompPubSubRedisTemplate")
    public RedisTemplate<String, String> stompPubSubRedisTemplate(
            @Qualifier("chattingConnectionFactory") RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // Key와 Value 모두 StringSerializer를 사용하여 JSON 문자열 그대로 처리
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "chattingConnectionFactory")
    public RedisConnectionFactory chattingConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setDatabase(9); // 채팅메시지 메시지 브로커 전용 DB 번호
        return new LettuceConnectionFactory(config);
    }
}
