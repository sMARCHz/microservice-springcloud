package com.nattanon.studentservice.response;

import lombok.Data;

@Data
public class AddressResponse {
    private long addressId;
    private String street;
    private String city;
}
