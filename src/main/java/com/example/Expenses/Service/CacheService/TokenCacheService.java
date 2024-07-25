package com.example.Expenses.Service.CacheService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenCacheService {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public TokenCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheInvalidToken(String token, long expirationTimeInSeconds) {
        redisTemplate.opsForValue().set(token, "invalid", expirationTimeInSeconds, TimeUnit.SECONDS);
    }

    public boolean isTokenCached(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
