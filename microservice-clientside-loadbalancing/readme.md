## Microservice with Spring Cloud and Client-side Load Balancing
This project is used for learning how to build microservice integrated with Client-side LoadBalancing.  
It consists of 3 services in the architecture.
1. Address Service
2. Student Service
3. Eureka Server  

**Note:** This project focus on Student Service only, so other services are from eureka project.

---

### What is Load Balancing?
Load Balancing is the process of distributing load(set of tasks/requests) or network load efficiently across multiple servers.  

Load Balancer acts like "traffic cop" sitting in front of servers deciding which server a particular request must be directed to based on some algorithm. 
It maximizes speed and capacity utilization and ensures that no server is overworked or idle.  

Load balancing which has load balancer in front of the servers  is called **Server-side Load Balancing**.

![Architecture with load balancer](https://www.nginx.com/wp-content/uploads/2014/07/what-is-load-balancing-diagram-NGINX-640x324.png)

#### But why we need Client-side Load Balancing?
Server-side Load Balancer has many disadvantages such as
- If load balancer is down, all the instances of microservice become inaccessible.
- Since each microservices have separate load balancer, the overall complexity of the system increases and it becomes hard to manage.
- The network latency increases as the number of hops. A microservice can't call other microservice directly. It needs to pass the load balancer before reach the target, so it increases network latency.


### What is Client-side Load Balancing?
Client-side Load Balancing is a load balancing process but has no load balancer in front of servers. 
Load Balancer logic is part of client itself. It decides which server a request must be directed to by itself.  

It solves all the problems of server-side load balancing.


### Configuration
pom.xml
```
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-loadbalancer</artifactId>
    </dependency>
</dependencies>
```

AddressServiceLoadBalancerConfig.java  (student-service)
```
@LoadBalancerClient(value = "address-service")
public class AddressServiceLoadBalancerConfig {

    @LoadBalanced  // Load Balance when use AddressFeignClients instance
    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
```

!!! Need to run 2 instance of address-service for testing load balancing

### Next station ⏩⏩  microservice-api-gateway
