## Microservice with Spring
This project is a **base project** of all projects in this repository.  
It consists of 2 simple services in the architecture.
1. Address Service
2. Student Service

**Note:** This project is used for learning microservice with Spring Cloud only, so I make it very simple. 
### 1. Address Service: &ensp;&ensp;/api/address
This service handles the Address component. In this service has only two HTTP requests.
>#### POST : &ensp;&ensp;/create
>Description : Create new address  
>Request body :
>``` 
>{
>   "street" : "Sukhumvit",  
>   "city" : "Bangkok"  
>}
>```
>Response :
>```
>{
>   "addressId" : 1,
>   "street" : "Sukhumvit",
>   "city" : "Bangkok"
>}
>```

>#### GET : &ensp;/getById/:id  
>Description : Get address by ID  
>Response :
>```
>{
>   "addressId" : 1,
>   "street" : "Sukhumvit",
>   "city" : "Bangkok"
>}
>```  

### 2. Student Service: &ensp;&ensp; /api/student
This service handles the Student component, and **It needs to communicate with Address component for data. (WebClient)**  
In this service has only two HTTP requests.
>#### POST : &ensp;&ensp;/create
>Description : Create new student  
>Request body :
>``` 
>{
>   "firstName" : "Nattanon",  
>   "lastName" : "Chansamakr",
>   "email" : "abc@gmail.com",
>   "addressId" : 1  
>}
>```
>Response :
>```
>{
>   "firstName" : "Nattanon",  
>   "lastName" : "Chansamakr",
>   "email" : "abc@gmail.com",
>   "address" : {
>       "addressId" : 1
>       "street" : "Sukhumvit",
>       "city" : "Bangkok"
>   }
>}
>```

>#### GET : &ensp;/getById/:id
>Description : Get student by ID  
>Response :
>```
>{
>   "firstName" : "Nattanon",  
>   "lastName" : "Chansamakr",
>   "email" : "abc@gmail.com",
>   "address" : {
>       "addressId" : 1
>       "street" : "Sukhumvit",
>       "city" : "Bangkok"
>   }
>}
>```  

### How to request data from Address?
I can use RestTemplate for HTTP request, but it's going to be deprecated soon. Therefore, I use WebClient of Spring Reactive instead.  
```
@Bean
public WebClient webClient() {
    return WebClient.builder().baseUrl(addressServiceBaseUrl).build();
}
```
```
private AddressResponse getAddressById(long addressId) {
    Mono<AddressResponse> addressResponse = webClient.get().uri("/getById/" + addressId).retrieve().bodyToMono(AddressResponse.class);
    return addressResponse.block();
}
```