package com.nattanon.studentservice.response;

import com.nattanon.studentservice.entity.Student;
import lombok.Data;

@Data
public class StudentResponse {
    private String firstName;
    private String lastName;
    private String email;
    private AddressResponse address;

    public StudentResponse(Student student) {
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
    }
}
