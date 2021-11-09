package com.nattanon.studentservice.service;

import com.nattanon.studentservice.entity.Student;
import com.nattanon.studentservice.repository.StudentRepository;
import com.nattanon.studentservice.request.CreateStudentRequest;
import com.nattanon.studentservice.response.AddressResponse;
import com.nattanon.studentservice.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private WebClient webClient;

    public StudentResponse createAddress(CreateStudentRequest createStudentRequest) {
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
        studentResponse.setAddress(getAddressById(id));
        return studentResponse;
    }

    private AddressResponse getAddressById(long addressId) {
        Mono<AddressResponse> addressResponse = webClient.get().uri("/getById/" + addressId).retrieve().bodyToMono(AddressResponse.class);
        return addressResponse.block();
    }
}