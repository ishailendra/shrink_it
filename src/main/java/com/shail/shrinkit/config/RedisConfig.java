package com.shail.shrinkit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

@Configuration
public class RedisConfig {

    @Value("${spring.cache.redis.host}")
    private String redisHost;

    @Value("${spring.cache.redis.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
        configuration.setPassword("test_db");
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheConfiguration cacheConfig = defaultCacheConfig(Duration.ofMinutes(10))
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(cacheConfig)
                .withCacheConfiguration("urls", defaultCacheConfig(Duration.ofDays(1)))
                .withCacheConfiguration("shrink_key", defaultCacheConfig(Duration.ofDays(5)))
                .build();
    }

    private RedisCacheConfiguration defaultCacheConfig(Duration duration) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(duration)
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer())
                );
    }
}
