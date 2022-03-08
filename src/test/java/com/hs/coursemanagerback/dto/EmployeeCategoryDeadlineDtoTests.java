package com.hs.coursemanagerback.dto;

import com.hs.coursemanagerback.model.employee.dto.EmployeeCategoryDeadlineDto;
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
public class EmployeeCategoryDeadlineDtoTests {

    @Autowired
    Validator validator;

    @Test
    public void When_Valid_Then_ViolationsEmpty() {
        EmployeeCategoryDeadlineDto employeeCategoryDeadlineDto = new EmployeeCategoryDeadlineDto();
        employeeCategoryDeadlineDto.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));

        Set<ConstraintViolation<EmployeeCategoryDeadlineDto>> violations = validator.validate(employeeCategoryDeadlineDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_DateIsNull_Should_ThrowConstraintViolationException() {
        EmployeeCategoryDeadlineDto employeeCategoryDeadlineDto = new EmployeeCategoryDeadlineDto();

        Set<ConstraintViolation<EmployeeCategoryDeadlineDto>> violations = validator.validate(employeeCategoryDeadlineDto);

        assertEquals(1, violations.size());
    }

    @Test
    public void When_DateIsNull_Then_ConstraintViolationMessage() {
        EmployeeCategoryDeadlineDto employeeCategoryDeadlineDto = new EmployeeCategoryDeadlineDto();

        Set<ConstraintViolation<EmployeeCategoryDeadlineDto>> violations = validator.validate(employeeCategoryDeadlineDto);

        assertEquals("categoryAssignmentDeadlineDate cannot be null", violations.iterator().next().getMessage());
    }

}
