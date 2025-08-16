package com.bitorax.priziq.configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SslOptions;
import io.lettuce.core.SocketOptions;

import java.time.Duration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedisConfiguration {
    @Value("${spring.data.redis.host}")
    String redisHost;

    @Value("${spring.data.redis.port}")
    int redisPort;

    @Value("${spring.data.redis.database}")
    int redisDatabase;

    @Value("${spring.data.redis.username:default}")
    String redisUsername;

    @Value("${spring.data.redis.password}")
    String redisPassword;

    @Value("${spring.data.redis.timeout}")
    long redisTimeout;

    @Value("${spring.data.redis.lettuce.shutdown-timeout}")
    long redisShutdownTimeout;

    @Value("${redis.ssl.enabled:false}")
    boolean redisSslEnabled;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(redisHost);
        standaloneConfig.setPort(redisPort);
        standaloneConfig.setDatabase(redisDatabase);
        standaloneConfig.setUsername(redisUsername);
        standaloneConfig.setPassword(RedisPassword.of(redisPassword));

        LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigBuilder = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(redisTimeout))
                .shutdownTimeout(Duration.ofMillis(redisShutdownTimeout))
                .clientOptions(ClientOptions.builder()
                        .autoReconnect(true)
                        .pingBeforeActivateConnection(true)
                        .socketOptions(SocketOptions.builder()
                                .connectTimeout(Duration.ofMillis(redisTimeout))
                                .build())
                        .build());

        if (redisSslEnabled) {
            clientConfigBuilder.useSsl()
                    .and()
                    .clientOptions(ClientOptions.builder()
                            .autoReconnect(true)
                            .pingBeforeActivateConnection(true)
                            .sslOptions(SslOptions.builder()
                                    .jdkSslProvider()
                                    .build())
                            .socketOptions(SocketOptions.builder()
                                    .connectTimeout(Duration.ofMillis(redisTimeout))
                                    .build())
                            .build());
        }

        LettuceConnectionFactory factory = new LettuceConnectionFactory(standaloneConfig, clientConfigBuilder.build());
        factory.setValidateConnection(true);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}