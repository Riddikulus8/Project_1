package com.example.sms.controller;

import com.example.sms.entity.Student;
import com.example.sms.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentMvcController {
    private final StudentService service;

    @GetMapping
    public String list(@RequestParam(required=false) String q,
                       @RequestParam(defaultValue="0") int page,
                       @RequestParam(defaultValue="10") int size,
                       Model model) {
        Page<Student> p = service.list(q, PageRequest.of(page, size));
        model.addAttribute("page", p);
        model.addAttribute("q", q);
        return "students/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("student") Student student, BindingResult result) {
        if (result.hasErrors()) return "students/form";
        service.create(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Student s = service.get(id).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Student not found"));
        model.addAttribute("student", s);
        return "students/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("student") Student student, BindingResult result) {
        if (result.hasErrors()) return "students/form";
        service.update(id, student);
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/students";
    }
}
