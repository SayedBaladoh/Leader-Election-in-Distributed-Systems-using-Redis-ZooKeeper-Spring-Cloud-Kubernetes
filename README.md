# Leader Election in Distributed Systems

An implementation of the Leader Election pattern with different approaches (Redis, ZooKeeper, Spring Cloud Kubernetes) for electing a leader in a multi-node cluster (distributed system) for ensuring only one instance (the Leader) will process the work (maybe the scheduled task or maybe coordination between different services etc.)

---

## Table of contents

* [Solutions](#solutions)
* [References](#references)
* [About me](#about-me)
* [Contributing](#contributing)
* [Acknowledgments](#acknowledgments)

---

## Solutions

Implementing leader election can be done using different approaches:

- **ZooKeeper**:

  Use ZooKeeper as the coordination service provides high reliability.

- **Spring Cloud Kubernetes**:

  If the application is deployed in Kubernetes, we can use its native leader election capabilities via Spring Cloud
  Kubernetes.

- Custom Leader Election with **Redis**:

  Redis is a lightweight and simple leader election in smaller-scale systems. It has SETNX (set if not exists) command
  we can use it for leader election.

### Implemented Solution

The implementation for the different approaches done in a separate project.

- [Leader Election in Distributed Systems using **Redis**](./leader-election-in-distributed-system-redis)
- [Leader Election in Distributed Systems using **ZooKeeper**](./leader-election-in-distributed-system-zookeeper)
- [Leader Election in Distributed Systems using **ZooKeeper** with **Apache Curator**](./leader-election-in-distributed-system-zookeeper-curator)

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
