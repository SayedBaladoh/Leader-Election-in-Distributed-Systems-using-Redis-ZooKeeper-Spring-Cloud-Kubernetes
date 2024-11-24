package com.sayedbaladoh.leaderelection.service;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LeaderElection extends LeaderSelectorListenerAdapter {

    @Value("${zookeeper.connect-string}")
    private String zookeeperConnectString;

    private static final String LEADER_ELECTION_PATH = "/leader-election";

    private CuratorFramework client;
    private LeaderSelector leaderSelector;
    private boolean isLeader = false;

    @PostConstruct
    public void init() {
        // Initialize Zookeeper client
        client = CuratorFrameworkFactory.newClient(zookeeperConnectString, new ExponentialBackoffRetry(1000, 3));
        client.start();

        // Initialize LeaderSelector
        leaderSelector = new LeaderSelector(client, LEADER_ELECTION_PATH, this);
        leaderSelector.autoRequeue(); // Requeue leadership after losing it
        leaderSelector.start();
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        log.info("Became the leader!");
        isLeader = true;

        // Leadership will continue until this method exits or the session is lost
        try {
            Thread.sleep(Long.MAX_VALUE); // Keep leadership
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            isLeader = false; // Leadership lost
        }
    }

    @PreDestroy
    public void shutdown() {
        if (leaderSelector != null) {
            leaderSelector.close();
        }
        if (client != null) {
            client.close();
        }
    }

    public boolean isCurrentInstanceLeader() {
        log.info("Current node, isLeader: {}", isLeader);
        return isLeader;
    }
}