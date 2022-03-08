package com.hs.coursemanagerback.dto;

import com.hs.coursemanagerback.model.employee.dto.EmployeeActiveDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/// Unit tests
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeActivePatchDtoTests {

    @Autowired
    Validator validator;

    @Test
    public void When_Valid_Then_ViolationsEmpty() {
        EmployeeActiveDto employeeActiveDto = new EmployeeActiveDto();
        employeeActiveDto.setActive(true);

        Set<ConstraintViolation<EmployeeActiveDto>> violations = validator.validate(employeeActiveDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_DateIsNull_Should_ThrowConstraintViolationException() {
        EmployeeActiveDto employeeActiveDto = new EmployeeActiveDto();

        Set<ConstraintViolation<EmployeeActiveDto>> violations = validator.validate(employeeActiveDto);

        assertEquals(1, violations.size());
    }

    @Test
    public void When_DateIsNull_Then_ConstraintViolationMessage() {
        EmployeeActiveDto employeeActiveDto = new EmployeeActiveDto();

        Set<ConstraintViolation<EmployeeActiveDto>> violations = validator.validate(employeeActiveDto);

        assertEquals("active cannot be null", violations.iterator().next().getMessage());
    }
}
