package com.sayedbaladoh.leaderelection.service;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class LeaderElection implements Watcher {

    @Value("${zookeeper.connect-string}")
    private String zookeeperConnectString;

    private static final String LEADER_ELECTION_PATH = "/leader-election";
    private static final String NODE_PATH = LEADER_ELECTION_PATH + "/candidate_";
    private static final int SESSION_TIMEOUT = 3000;

    private ZooKeeper zooKeeper;
    private String currentZnode;
    private boolean isLeader = false;

    @PostConstruct
    public void init() throws IOException, KeeperException, InterruptedException {
        // Connect to Zookeeper
        zooKeeper = new ZooKeeper(zookeeperConnectString, SESSION_TIMEOUT, this);

        // Ensure the leader-election path exists
        createNodeIfNotExists(LEADER_ELECTION_PATH, CreateMode.PERSISTENT);

        // Participate in leader election
        participateInLeaderElection();
    }

    private String createNodeIfNotExists(String path, CreateMode createMode) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path, false);
        if (stat == null) {
            String createdNodePath = zooKeeper.create(
                    path,
                    new byte[0],
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    createMode
            );
            log.info("Node created, base path: {}, full path: {}.", path, createdNodePath);
            return createdNodePath;
        } else {
            return path;
        }
    }

    private void participateInLeaderElection() throws KeeperException, InterruptedException {
        // Create an ephemeral sequential node
        String znodeFullPath = createNodeIfNotExists(NODE_PATH, CreateMode.EPHEMERAL_SEQUENTIAL);
        this.currentZnode = znodeFullPath.substring(LEADER_ELECTION_PATH.length() + 1);
        log.info("Created node: {}.", this.currentZnode);

        // Check if this node is the leader
        checkLeadership();
    }

    private void checkLeadership() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(LEADER_ELECTION_PATH, false);

        // Sort the children nodes by sequence number
        Collections.sort(children);

        if (children.get(0).equals(currentZnode)) {
            isLeader = true;
            log.info("This instance ({}) is the leader.", currentZnode);
        } else {
            isLeader = false;
            log.info("This instance ({}) is not the leader. Watching node: {}.", currentZnode, children.get(0));

            // Watch the node just before this one
            watchPredecessorNode(children);
        }
    }

    private void watchPredecessorNode(List<String> children) throws KeeperException, InterruptedException {
        // Watch the node just before this one
        int index = children.indexOf(currentZnode);
        String predecessorNode = children.get(index - 1);

        zooKeeper.exists(
                LEADER_ELECTION_PATH + "/" + predecessorNode,
                true // Set watch on predecessor
        );
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDeleted) {
            try {
                checkLeadership();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        if (currentZnode != null) {
            try {
                zooKeeper.delete(LEADER_ELECTION_PATH + "/" + currentZnode, -1);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        zooKeeper.close();
    }

    public boolean isCurrentInstanceLeader() {
        log.info("Current node: {}, isLeader: {}", currentZnode, isLeader);
        return isLeader;
    }
}


