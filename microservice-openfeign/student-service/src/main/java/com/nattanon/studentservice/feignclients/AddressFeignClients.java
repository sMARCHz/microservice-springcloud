package com.nattanon.studentservice.feignclients;

import com.nattanon.studentservice.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${address.service.url}", value = "address-service", path = "/api/address")
public interface AddressFeignClients {

    @GetMapping("/getById/{id}")
    AddressResponse getById(@PathVariable long id);
}
