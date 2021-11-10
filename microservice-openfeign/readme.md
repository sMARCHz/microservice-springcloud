## Microservice with Spring Cloud and OpenFeign
This project is used for learning how to build microservice integrated with OpenFeign.  
It consists of 2 simple services in the architecture.
1. Address Service
2. Student Service (with OpenFeign)

**Note:** This project is extended from base project.  
**Note:** OpenFeign isn't in the Netflix OSS.  

---
### What is OpenFeign?
Feign is a **declarative** web service client. It makes writing web service clients easier. It has pluggable annotation support including Feign annotations and JAX-RS annotations. Also supports pluggable encoders and decoders.  

### Why we should use OpenFeign?  
Feign has many advantages, such as
- Easier to read.
- Provide an easy way to call REST services by making a simple Java function call.
- The actual implementation of making a REST call is handled at runtime by Feign, so the implementation can be configured without changing the business logic code.
- Integrates with Ribbon and Eureka Automatically.
- Etc.

### Difference between using OpenFeign and not using

Using :
```
AddressResponse address = addressFeignClients.getById(id);
```
Not using : 
```
AddressResponse address = getAddressById(id);
```
```
private AddressResponse getAddressById(long addressId) {
    Mono<AddressResponse> addressResponse = webClient.get().uri("/getById/" + addressId).retrieve().bodyToMono(AddressResponse.class);
    return addressResponse.block();
}

@Value("${address.service.url}")
private String addressServiceBaseUrl;

@Bean
public WebClient webClient() {
    return WebClient.builder().baseUrl(addressServiceBaseUrl).build();
}
```

### How to configure OpenFeign?
pom.xml
``` 
<properties>
    <spring-cloud.version>2020.0.4</spring-cloud.version>
</properties>

<dependencies>
    ...
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
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
ServiceApplication.java &ensp;&ensp;&ensp;(add @EnableFeignClients)
```
@SpringBootApplication
@EnableFeignClients
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
```
AddressFeignClients.java
```
@FeignClient(url = "${address.service.url}", value = "address-service", path = "/api/address")
public interface AddressFeignClients {

    @GetMapping("/getById/{id}")
    AddressResponse getById(@PathVariable long id);
}
```
