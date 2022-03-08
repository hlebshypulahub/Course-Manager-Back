package com.hs.coursemanagerback.dto;

import com.hs.coursemanagerback.model.employee.dto.EmployeeExemptionDto;
import com.hs.coursemanagerback.model.enumeration.Exemption;
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
public class EmployeeExemptionDtoTests {

    @Autowired
    Validator validator;

    private EmployeeExemptionDto employeeExemptionDto;

    @BeforeEach
    public void before() {
        employeeExemptionDto = new EmployeeExemptionDto();
        employeeExemptionDto.setExemption(Exemption.LESS_THAN_YEAR_WORK);
        employeeExemptionDto.setExemptionStartDate(LocalDate.of(2020, 5, 5));
        employeeExemptionDto.setExemptionEndDate(LocalDate.of(2020, 6, 6));
    }

    @AfterEach
    public void after() {
        employeeExemptionDto = null;
    }

    @Test
    public void When_Valid_Then_ViolationsEmpty() {
        Set<ConstraintViolation<EmployeeExemptionDto>> violations = validator.validate(employeeExemptionDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_ExemptionIsNull_Should_ThrowConstraintViolationException() {
        employeeExemptionDto.setExemption(null);

        Set<ConstraintViolation<EmployeeExemptionDto>> violations = validator.validate(employeeExemptionDto);

        assertEquals("ExemptionNotNull constraint violation", violations.iterator().next().getMessage());
    }

    @Test
    public void When_StartDateIsNull_Should_ThrowConstraintViolationException() {
        employeeExemptionDto.setExemptionStartDate(null);

        Set<ConstraintViolation<EmployeeExemptionDto>> violations = validator.validate(employeeExemptionDto);

        assertEquals("ExemptionNotNull constraint violation", violations.iterator().next().getMessage());
    }

    @Test
    public void When_EndDateIsNull_Then_ViolationsEmpty() {
        employeeExemptionDto.setExemptionEndDate(null);

        Set<ConstraintViolation<EmployeeExemptionDto>> violations = validator.validate(employeeExemptionDto);

        assertTrue(violations.isEmpty());
    }

}
