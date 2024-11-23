package com.sayedbaladoh.leaderelection.service;

import com.sayedbaladoh.leaderelection.repository.LeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaderElectionService {

    private static final String INSTANCE_ID = "instance-" + System.currentTimeMillis();

    private final LeaderRepository leaderRepository;

    public boolean isCurrentInstanceLeader() {
        return isCurrentLeader() || tryToBecomeLeader();
    }

    @Scheduled(fixedRate = 5000)
    public void sendHeartbeat() {
        // Extend the leader key's expiration time to maintain leadership
        if (isCurrentLeader()) {
            log.info("Extend the leader {} expiration time.", INSTANCE_ID);
            leaderRepository.maintainLeadership();
        }
    }

    private boolean tryToBecomeLeader() {
        log.info("Trying {} to become a leader.", INSTANCE_ID);
        return leaderRepository.setLeader(INSTANCE_ID);
    }

    private boolean isCurrentLeader() {
        var isLeader = INSTANCE_ID.equals(leaderRepository.getLeader());
        log.info("Is current {} leader: {}.", INSTANCE_ID, isLeader);
        return isLeader;
    }

}
