package com.hs.coursemanagerback.dto;

import com.hs.coursemanagerback.model.employee.dto.EmployeeEducationDto;
import com.hs.coursemanagerback.model.enumeration.Education;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/// Unit tests
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeEducationDtoTests {

    @Autowired
    Validator validator;

    private EmployeeEducationDto employeeEducationDto;

    @BeforeEach
    public void before() {
        employeeEducationDto = new EmployeeEducationDto();
        employeeEducationDto.setEducation(Education.HIGHER);
        employeeEducationDto.setEduName("AGH");
        employeeEducationDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));
    }

    @AfterEach
    public void after() {
        employeeEducationDto = null;
    }

    @Test
    public void When_Valid_Then_ViolationsEmpty() {
        Set<ConstraintViolation<EmployeeEducationDto>> violations = validator.validate(employeeEducationDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_EducationIsNull_Should_ThrowConstraintViolationException() {
        employeeEducationDto.setEducation(null);

        Set<ConstraintViolation<EmployeeEducationDto>> violations = validator.validate(employeeEducationDto);

        assertEquals("Education cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void When_EduNameIsBlank_Should_ThrowConstraintViolationException() {
        employeeEducationDto.setEduName("  ");

        Set<ConstraintViolation<EmployeeEducationDto>> violations = validator.validate(employeeEducationDto);

        assertEquals("Education name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void When_EduGraduationDateIsNull_Should_ThrowConstraintViolationException() {
        employeeEducationDto.setEduGraduationDate(null);

        Set<ConstraintViolation<EmployeeEducationDto>> violations = validator.validate(employeeEducationDto);

        assertEquals("Education graduation date cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    public void When_EverythingIsNullOrBlank_Should_ThrowConstraintViolationException() {
        employeeEducationDto.setEducation(null);
        employeeEducationDto.setEduGraduationDate(null);
        employeeEducationDto.setEduName("  ");

        Set<ConstraintViolation<EmployeeEducationDto>> violations = validator.validate(employeeEducationDto);

        assertEquals(3, violations.size());
    }
}
