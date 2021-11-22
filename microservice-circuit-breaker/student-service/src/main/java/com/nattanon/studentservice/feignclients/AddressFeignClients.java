package com.nattanon.studentservice.feignclients;

import com.nattanon.studentservice.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "api-gateway")
public interface AddressFeignClients {

    @GetMapping("/address-service/api/address/getById/{id}")
    AddressResponse getById(@PathVariable long id);
}
