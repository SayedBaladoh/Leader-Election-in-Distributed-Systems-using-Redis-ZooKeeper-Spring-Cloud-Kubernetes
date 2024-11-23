package com.sayedbaladoh.leaderelection.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class LeaderRepository {

    private static final String LEADER_KEY = "file-cleanup-leader";
    private static final long LEADER_TTL = 15;

    private final ValueOperations<String, String> valueOperations;

    public LeaderRepository(StringRedisTemplate redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    public boolean setLeader(String instanceId) {
        var isSet = valueOperations.setIfAbsent(LEADER_KEY, instanceId, LEADER_TTL, TimeUnit.SECONDS);
        log.info("Is {} became a leader: {}.", instanceId, isSet);
        return Boolean.TRUE.equals(isSet);
    }

    public String getLeader() {
        return valueOperations.get(LEADER_KEY);
    }

    public void maintainLeadership() {
        // Extend the leader key's expiration time to maintain leadership
        // redisTemplate.expire(LEADER_KEY, LEADER_TTL, TimeUnit.SECONDS);
        valueOperations.getAndExpire(LEADER_KEY, LEADER_TTL, TimeUnit.SECONDS);
    }
}
