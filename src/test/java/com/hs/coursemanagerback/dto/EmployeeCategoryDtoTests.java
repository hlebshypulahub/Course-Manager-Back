package com.hs.coursemanagerback.dto;

import com.hs.coursemanagerback.model.employee.dto.EmployeeCategoryDto;
import com.hs.coursemanagerback.model.enumeration.Category;
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
public class EmployeeCategoryDtoTests {

    @Autowired
    Validator validator;

    private EmployeeCategoryDto employeeCategoryDto;

    @BeforeEach
    public void before() {
        employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setCategory(Category.FIRST);
        employeeCategoryDto.setCategoryNumber("228");
        employeeCategoryDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryDto.setQualification("Farmaceuta");
    }

    @AfterEach
    public void after() {
        employeeCategoryDto = null;
    }

    @Test
    public void When_Valid_Then_ViolationsEmpty() {
        Set<ConstraintViolation<EmployeeCategoryDto>> violations = validator.validate(employeeCategoryDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_QualificationIsBlank_Should_ThrowConstraintViolationException() {
        employeeCategoryDto.setQualification(" ");

        Set<ConstraintViolation<EmployeeCategoryDto>> violations = validator.validate(employeeCategoryDto);

        assertEquals("qualification cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void When_CategoryIsNull_Should_ThrowConstraintViolationException() {
        employeeCategoryDto.setCategory(null);

        Set<ConstraintViolation<EmployeeCategoryDto>> violations = validator.validate(employeeCategoryDto);

        assertEquals("category cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void When_CategoryAssignmentDateIsNull_And_CategoryIsNotNone_Should_ThrowConstraintViolationException() {
        employeeCategoryDto.setCategoryAssignmentDate(null);

        Set<ConstraintViolation<EmployeeCategoryDto>> violations = validator.validate(employeeCategoryDto);

        assertEquals("CategoryAssignmentDateNotNull constraint violation", violations.iterator().next().getMessage());
    }

    @Test
    public void When_CategoryNumberIsBlank_And_CategoryIsNotNone_Should_ThrowConstraintViolationException() {
        employeeCategoryDto.setCategoryNumber("");

        Set<ConstraintViolation<EmployeeCategoryDto>> violations = validator.validate(employeeCategoryDto);

        assertEquals("CategoryNumberNotBlank constraint violation", violations.iterator().next().getMessage());
    }

    @Test
    public void When_CategoryNumberIsBlank_And_CategoryAssignmentDateIsNull_And_CategoryIsNotNone_Should_ThrowConstraintViolationException() {
        employeeCategoryDto.setCategoryNumber("");
        employeeCategoryDto.setCategoryAssignmentDate(null);

        Set<ConstraintViolation<EmployeeCategoryDto>> violations = validator.validate(employeeCategoryDto);

        assertEquals(2, violations.size());
    }

    @Test
    public void When_CategoryNumberIsBlank_And_CategoryAssignmentDateIsNull_And_CategoryIsNone_Then_ViolationsEmpty() {
        employeeCategoryDto.setCategory(Category.NONE);
        employeeCategoryDto.setCategoryNumber("");
        employeeCategoryDto.setCategoryAssignmentDate(null);

        Set<ConstraintViolation<EmployeeCategoryDto>> violations = validator.validate(employeeCategoryDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void When_EverythingIsNull_Should_ThrowConstraintViolationException() {
        employeeCategoryDto.setCategory(null);
        employeeCategoryDto.setQualification(null);
        employeeCategoryDto.setCategoryNumber(null);
        employeeCategoryDto.setCategoryAssignmentDate(null);

        Set<ConstraintViolation<EmployeeCategoryDto>> violations = validator.validate(employeeCategoryDto);

        assertEquals(4, violations.size());
    }
}
