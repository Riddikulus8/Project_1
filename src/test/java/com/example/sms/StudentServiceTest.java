package com.example.sms;

import com.example.sms.entity.Student;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentServiceTest {
    @Test
    void listReturnsAllWhenNoQuery() {
        var repo = Mockito.mock(StudentRepository.class);
        var svc = new StudentServiceImpl(repo);
        var pageable = PageRequest.of(0, 10);

        Mockito.when(repo.findAll(pageable)).thenReturn(new PageImpl<>(List.of()));

        Page<Student> result = svc.list(null, pageable);

        assertThat(result.getContent()).isEmpty();
    }
}
