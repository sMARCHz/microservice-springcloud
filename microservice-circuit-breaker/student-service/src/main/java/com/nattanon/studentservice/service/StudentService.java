package com.nattanon.studentservice.service;

import com.nattanon.studentservice.entity.Student;
import com.nattanon.studentservice.feignclients.AddressFeignClients;
import com.nattanon.studentservice.repository.StudentRepository;
import com.nattanon.studentservice.request.CreateStudentRequest;
import com.nattanon.studentservice.response.AddressResponse;
import com.nattanon.studentservice.response.StudentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AddressFeignClients addressFeignClients;

    public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {
        Student student = new Student();
        student.setFirstName(createStudentRequest.getFirstName());
        student.setLastName(createStudentRequest.getLastName());
        student.setEmail(createStudentRequest.getEmail());
        student.setAddressId(createStudentRequest.getAddressId());
        studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse(student);
        studentResponse.setAddress(getAddressById(student.getAddressId()));
        return studentResponse;
    }

    public StudentResponse getById(long id) {
        Student student = studentRepository.getById(id);
        StudentResponse studentResponse = new StudentResponse(student);
        studentResponse.setAddress(getAddressById(student.getAddressId()));
        return studentResponse;
    }

    @CircuitBreaker(name = "addressService", fallbackMethod = "fallbackGetAddressById")
    private AddressResponse getAddressById(long addressId) {
        return addressFeignClients.getById(addressId);
    }

    private AddressResponse fallbackGetAddressById(long addressId, Throwable th) {
        return new AddressResponse();
    }
}