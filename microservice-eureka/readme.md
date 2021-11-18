## Microservice with Spring Cloud and Netflix Eureka
This project is used for learning how to build microservice integrated with Eureka.  
It consists of 2 simple services in the architecture.
1. Address Service
2. Student Service (with OpenFeign)
3. Eureka Server  

**Note:** This project is extended from OpenFeign project.

---
### Why Hardcoding URLs is bad?  
- **Changes require code updates**  
If only one microservice url has changed, we need to update all other services that communicate with it.  
- **Dynamic URL in the cloud**  
When we deploy on cloud, we have the dynamic URLs that changes when we stop and start server again. We can't predict the URLs.  
- **Load balancing**  
If we have many instances, each instance might have different URLs. It's very inefficient to hardcode the URLs.
- **Multiple environments**  
  The URLs changes in each phase of the software engineering process, i.e. the URLs might be different during DEV, SIT, UAT, and Production.

According to the problems, we need some technologies to cope with these problems.  
Netflix Eureka comes to take the role.

---
### What is Eureka?
Eureka is the Netflix Service Registration and Discovery. It allows microservices to register and discover other services for communicating without hardcoding URLs (Hostname and Port).  
Eureka mainly consists of 2 components  
1. Eureka Server
2. Eureka Client

### 1. Eureka Server
It is an application that contains information about all client service applications. Each microservice is registered with the Eureka server and Eureka knows all the client applications running on each port and IP address. Eureka Server is also known as Discovery Server.
#### Configure Eureka Server
pom.xml
```
<properties>
    <spring-cloud.version>2020.0.4</spring-cloud.version>
</properties>

<dependencies>
    ...
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
``` 
application.properties
```
spring.application.name=eureka-server
eureka.client.registerWithEureka=false 
eureka.client.fetchRegistry=false      
```
EurekaServerApplication.java &ensp;&ensp;&ensp;(add @EnableEurekaServer)
```
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

### 2. Eureka Clients
It's the actual microservice, and it registers with the Eureka Server, so if any other microservice wants the Eureka Client’s address then they’ll contact the Eureka Server. All operations with Eureka Client may take some time to be reflected on Eureka Server, and then on other Eureka Clients.
#### No Hardcoding URLs Anymore!!
We can use service/application name instead of URLs.
```
// After
@FeignClient(value = "address-service", path = "/api/address")
public interface AddressFeignClients {

    @GetMapping("/getById/{id}")
    AddressResponse getById(@PathVariable long id);
}

// Before
@FeignClient(url = "address.service.url", value = "address-service", path = "/api/address")
public interface AddressFeignClients {

    @GetMapping("/getById/{id}")
    AddressResponse getById(@PathVariable long id);
}
```
#### Configure Eureka Clients
pom.xml
```
<properties>
    <spring-cloud.version>2020.0.4</spring-cloud.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
application.properties
```
spring.application.name=student-service
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```
ServiceApplication.java &ensp;&ensp;&ensp;(add @EnableEurekaClient)
```
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
```

### Next station ⏩⏩  &ensp;microservice-clientside-loadbalancer