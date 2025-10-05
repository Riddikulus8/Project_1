package com.example.sms.controller; // <-- Changed package to match the controller

import com.example.sms.controller.StudentMvcController;
import com.example.sms.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentMvcController.class) // Corrected: target the correct class name
class StudentMvcTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    StudentService service;

    @Test
    @WithMockUser(username = "teacher", roles = {"TEACHER"}) // Simulates required login
    void listPageLoads() throws Exception {
        Mockito.when(service.list(Mockito.any(), Mockito.any()))
               .thenReturn(new PageImpl<>(java.util.List.of()));

        // Asserts that the secured /students endpoint is accessible and returns HTTP 200
        mvc.perform(get("/students"))
           .andExpect(status().isOk());
    }
}