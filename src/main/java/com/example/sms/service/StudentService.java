package com.example.sms.service;

import com.example.sms.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {
    Page<Student> list(String q, Pageable pageable);
    Student create(Student s);
    Optional<Student> get(Long id);
    Student update(Long id, Student s);
    void delete(Long id);
}
