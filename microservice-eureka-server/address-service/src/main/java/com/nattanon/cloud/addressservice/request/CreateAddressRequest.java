package com.nattanon.cloud.addressservice.request;

import lombok.Data;

@Data
public class CreateAddressRequest {
    private String street;
    private String city;
}
