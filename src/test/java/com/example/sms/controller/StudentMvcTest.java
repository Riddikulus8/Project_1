package com.example.sms.controller;

import com.example.sms.controller.StudentController;
import com.example.sms.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)   // test only StudentController
@AutoConfigureMockMvc(addFilters = false)   // disables Spring Security filters
class StudentMvcTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    StudentService service;

    @Test
    @WithMockUser(username = "teacher", roles = {"TEACHER"}) // fake logged-in user
    void listPageLoads() throws Exception {
        Mockito.when(service.list(Mockito.any(), Mockito.any()))
               .thenReturn(new PageImpl<>(java.util.List.of()));

        mvc.perform(get("/students"))
           .andExpect(status().isOk());
    }
}
