package com.example.sms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = "students",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_student_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_student_phone", columnNames = "phone")
    }
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 60, message = "First name must not exceed 60 characters")
    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 60, message = "Last name must not exceed 60 characters")
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @Email(message = "Must be a valid email address")
    @NotBlank(message = "Email is required")
    @Size(max = 120, message = "Email must not exceed 120 characters")
    @Column(nullable = false, length = 120)
    private String email;

    @NotBlank(message = "Department is required")
    @Size(max = 80, message = "Department must not exceed 80 characters")
    @Column(nullable = false, length = 80)
    private String department;

    @PastOrPresent(message = "Enrollment date cannot be in the future")
    private LocalDate enrollmentDate;

    @DecimalMin(value = "0.0", message = "GPA must be >= 0")
    @DecimalMax(value = "10.0", message = "GPA must be <= 10")
    private BigDecimal gpa;

    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Phone number must be exactly 10 digits"
    )
    @Column(name = "phone", length = 255, unique = true, nullable = false)
    private String phone;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
