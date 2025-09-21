package com.example.sms.service.impl;

import com.example.sms.entity.Student;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    @Override
    @Transactional(readOnly = true)
    public Page<Student> list(String q, Pageable pageable) {
        if (q == null || q.isBlank()) {
            return repo.findAll(pageable);
        }
        return repo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(q, q, pageable);
    }

    @Override
    public Student create(Student s) {
        if (repo.existsByEmail(s.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + s.getEmail());
        }
        return repo.save(s);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.Optional<Student> get(Long id) {
        return repo.findById(id);
    }

    @Override
    public Student update(Long id, Student s) {
        Student existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        existing.setFirstName(s.getFirstName());
        existing.setLastName(s.getLastName());
        existing.setEmail(s.getEmail());
        existing.setDepartment(s.getDepartment());
        existing.setEnrollmentDate(s.getEnrollmentDate());
        existing.setGpa(s.getGpa());
        existing.setPhone(s.getPhone());

        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Student not found");
        }
        repo.deleteById(id);
    }
}
