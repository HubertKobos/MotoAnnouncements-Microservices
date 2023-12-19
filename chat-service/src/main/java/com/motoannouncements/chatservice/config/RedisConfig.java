package com.motoannouncements.chatservice.config;

import com.motoannouncements.chatservice.chat.MessageDto;
import com.motoannouncements.chatservice.receiver.RedisReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.UUID;
import java.util.concurrent.Executors;

@Configuration
public class RedisConfig {
    @Bean
    JedisConnectionFactory connectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName("localhost");
        factory.setPort(6380);
        return factory;
    }

    @Bean
    public RedisTemplate<String, MessageDto> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, MessageDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Configure value serializer for MessageDto
        Jackson2JsonRedisSerializer<MessageDto> serializer = new Jackson2JsonRedisSerializer<>(MessageDto.class);
        template.setValueSerializer(serializer);

        // Configure key serializer (use StringRedisSerializer for keys)
        template.setKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
    @Bean
    ChannelTopic topic(){
        return new ChannelTopic("/topic/asd");
    }

    @Bean
    RedisMessageListenerContainer redisContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addMessageListener(new MessageListenerAdapter(new RedisReceiver()), topic());
        container.setTaskExecutor(Executors.newFixedThreadPool(4));
        return container;
    }
    @Bean
    public BoundListOperations<String, MessageDto> messageListOperations(RedisTemplate<String, MessageDto> redisTemplate) {
        return redisTemplate.boundListOps("messages");
    }
}
