# Leader Election in Distributed Systems using ZooKeeper

A simple implementation of the Leader Election pattern using ZooKeeper for electing a leader in a multi-node cluster (distributed system) for ensuring only one instance (the Leader) will process the work (maybe the scheduled task or maybe
coordination between different services etc.)

---

## Table of contents

* [Solutions](#solutions)
* [Technologies](#technologies)
* [Prerequisites](#prerequisites)
* [Getting Started](#getting-started)
* [Future Enhancements](#future-enhancements)
* [References](#references)
* [About me](#about-me)
* [Contributing](#contributing)
* [Acknowledgments](#acknowledgments)

---

## Solutions

Implementing leader election can be done using different approaches:

- **ZooKeeper**:

- Use ZooKeeper as the coordination service provides high reliability.

- **Spring Cloud Kubernetes**:

  If the application is deployed in Kubernetes, we can use its native leader election capabilities via Spring Cloud
  Kubernetes.

- Custom Leader Election with **Redis**:

  Redis is a lightweight and simple leader election in smaller-scale systems. It has SETNX (set if not exists) command
  we can use it for leader election.

### Implemented Solution

In this project we will implement leader election using **ZooKeeper** with **Apache Curator**.

**Apache Zookeeper** is an open-source coordination and synchronization service used primarily in distributed systems. It offers a reliable, highly available method for synchronizing tasks and simplifies the leader election process, ensuring high availability, fault tolerance, and data consistency.

**Apache Curator** is a Java/JVM client library for Apache ZooKeeper, a distributed coordination service. It includes a high level API framework and utilities to make using Apache ZooKeeper much easier and more reliable. It also includes recipes for common use cases and extensions such as service discovery and a Java 8 asynchronous DSL.

---

## Technologies

This project is created using the following technologies:

1. Java 21
2. Maven Dependency Management
3. Spring Boot
4. ZooKeeper
5. Apache Curator
6. Lombok

---

## Prerequisites

- [Java 21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/install.html)
- [Zookeeper](https://zookeeper.apache.org/doc/r3.1.2/zookeeperStarted.html)

---

## Getting Started

### **Clone the Repository**

```bash
git clone https://github.com/SayedBaladoh/Leader-Election-in-Distributed-Systems-using-Redis-ZooKeeper-Spring-Cloud-Kubernetes.git
cd Leader-Election-in-Distributed-Systems
cd leader-election-in-distributed-system-zookeeper-curator
```

### **Running Locally**

#### 1. **Start ZooKeeper Locally**
Start ZooKeeper server

You can use docker
```shell
docker run -p 2181:2181 --name zookeeper --restart always -d zookeeper

```
#### 2. **Update Application Configuration**

The application is configured via `application.yml`:

```yaml
zookeeper:
  connect-string: ${ZOOKEEPER_HOST:localhost}:${ZOOKEEPER_PORT:2181}

scheduler:
  cycle-time-in-minutes: ${CYCLE_TIME_IN_MINUTES:10}
```

##### Environment Variables

- `ZOOKEEPER_HOST`: ZooKeeper hostname (default: `localhost`).
- `ZOOKEEPER_PORT`: ZooKeeper port (default: `2181`).
- `CYCLE_TIME_IN_MINUTES`: Scheduler cycle time in minutes (default: `10`).

#### 3. **Build and Run**

```bash
mvn clean package
```

Start **multi instance** of the application locally with different port,
in different terminals run:

```shell
java -jar target/leader-election-in-distributed-systems-1.0.0.jar --server.port=8081
java -jar target/leader-election-in-distributed-systems-1.0.0.jar --server.port=8082
java -jar target/leader-election-in-distributed-systems-1.0.0.jar --server.port=8083
java -jar target/leader-election-in-distributed-systems-1.0.0.jar --server.port=8084
java -jar target/leader-election-in-distributed-systems-1.0.0.jar --server.port=8085
```

Observe logs:
- One instance will log `Became the leader!` and periodically run the scheduled task.
- Other instances will log `Current instance is not the leader. Skipping scheduled task.`.
- Stop the leader instance.
- Another instance will log `This instance ($node) is the leader.`. and take over the task.

---

## Future Enhancements

- Add unit and integration test.

---

## References

- [Leader Election Using Spring-Boot](https://allanvital.com/leader-election-using-spring-boot/)
- [Distributed Locking with Redis](https://carlosbecker.com/posts/distributed-locks-redis/)
- [Using Leader Election with Spring Cloud Kubernetes and Spring Scheduler](https://medium.com/@pedrommj8/using-leader-election-with-spring-cloud-kubernetes-and-spring-scheduler-8f7ea3e3e694)
- [Distributed locks and synchronizers](https://redisson.org/docs/data-and-services/locks-and-synchronizers/)
- [Spring Leader Election](https://docs.spring.io/spring-cloud-kubernetes/docs/current/reference/html/leader-election.html)
- [Zookeeper Tutorial — With Practical Example](https://bikas-katwal.medium.com/zookeeper-introduction-designing-a-distributed-system-using-zookeeper-and-java-7f1b108e236e)
- [Navigating the Jungle of Distributed Systems: A Guide to ZooKeeper and Leader Election Algorithms](https://hewi.blog/navigating-the-jungle-of-distributed-systems-a-guide-to-zookeeper-and-leader-election-algorithms)
- [Leader election in a Distributed System Using ZooKeeper](https://www.geeksforgeeks.org/leader-election-in-a-distributed-system-using-zookeeper/)
- [A practical example of the leader election in distributed systems](https://tolonbekov.medium.com/a-practical-example-of-the-leader-election-process-in-distributed-systems-2e1ce9aa42a6)
- [Leader election in a distributed system using ZooKeeper](https://medium.com/@minhaz1217/leader-election-in-a-distributed-system-using-zookeeper-b562e6d79855)

---

## About me

I am Sayed Baladoh - Phd. Tech Lead / Principal Software Engineer. I like software development. You can contact me via:

* [LinkedIn](https://www.linkedin.com/in/sayedbaladoh/)
* [Mail](mailto:sayedbaladoh@yahoo.com)
* [Phone +20 1004337924](tel:+201004337924)

**Any improvement or comment about the project is always welcome! As well as others shared their code publicly I want to
share mine! Thanks!**_

---

## Contributing

Any improvement or comment about the project is always welcome! Please create a pull request or submit an issue for any
suggestions.

---

## Acknowledgments

Thanks for reading. Share it with someone you think it might be helpful.