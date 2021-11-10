package com.nattanon.studentservice.request;

import lombok.Data;

@Data
public class CreateStudentRequest {
    private String firstName;
    private String lastName;
    private String email;
    private long addressId;
}
