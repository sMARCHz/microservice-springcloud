package com.nattanon.studentservice.repository;

import com.nattanon.studentservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
