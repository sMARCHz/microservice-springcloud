package com.nattanon.studentservice.service;

import com.nattanon.studentservice.feignclients.AddressFeignClients;
import com.nattanon.studentservice.response.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    Logger logger = LoggerFactory.getLogger(CommonService.class);

    long count = 1;

    @Autowired
    private AddressFeignClients addressFeignClients;

    @CircuitBreaker(name = "addressService", fallbackMethod = "fallbackGetAddressById")
    public AddressResponse getAddressById(long addressId) {
        logger.info("Count: {}", count);
        count++;
        return addressFeignClients.getById(addressId);
    }

    public AddressResponse fallbackGetAddressById(long addressId, Throwable th) {
        logger.error("Error: {}", th);
        return new AddressResponse();
    }
}
