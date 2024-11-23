package com.sayedbaladoh.leaderelection.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {

    private final LeaderElectionService leaderElectionService;

    @Scheduled(fixedRateString = "${scheduler.cycle-time-in-minutes}", timeUnit = TimeUnit.MINUTES)
    public void handleScheduledTask() {
        log.info("Running scheduled task.");
        if (leaderElectionService.isCurrentInstanceLeader()) {
            process();
        } else {
            log.info("Current instance is not the leader. Skipping scheduled task.");
        }
    }

    private void process() {
        log.info("Processing the scheduled task.....");
    }
}
