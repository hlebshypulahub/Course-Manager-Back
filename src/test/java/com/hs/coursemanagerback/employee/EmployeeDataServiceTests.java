package com.hs.coursemanagerback.employee;

import com.hs.coursemanagerback.exception.EducationNotValidException;
import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.model.employee.dto.EmployeeCategoryDto;
import com.hs.coursemanagerback.model.employee.dto.EmployeeEducationDto;
import com.hs.coursemanagerback.model.employee.dto.EmployeePatchDto;
import com.hs.coursemanagerback.model.enumeration.Category;
import com.hs.coursemanagerback.model.enumeration.Education;
import com.hs.coursemanagerback.repository.EmployeeRepository;
import com.hs.coursemanagerback.service.course.CourseService;
import com.hs.coursemanagerback.service.employee.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

/// Unit tests
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeDataServiceTests {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    CourseService courseService;
    @Mock
    EmployeeValidationService employeeValidationService;
    @Mock
    EmployeeExemptionService employeeExemptionService;
    @Mock
    EmployeeFilteringService employeeFilteringService;
    @Mock
    EmployeeCategoryService employeeCategoryService;

    private EmployeeDataService employeeDataService;

    private Employee employee;

    @BeforeEach
    public void before() {
        employeeDataService = new EmployeeDataService(employeeRepository, courseService, employeeValidationService,
                employeeExemptionService, employeeFilteringService, employeeCategoryService);

        employee = new Employee();
        employee.setForeignId(1L);
        employee.setFullName("Test");
        employee.setHiringDate(LocalDate.of(2020, 5, 5));
        employee.setJobFacility("Apteka 9");
        employee.setPosition("Farmaceuta");

        Optional<Employee> optionalEmployee = Optional.of(employee);
        doReturn(optionalEmployee).when(employeeRepository).findById(any(Long.class));
    }

    @AfterEach
    public void after() {
        employee = null;
    }

    @Test
    public void Should_SaveEmployee_When_Patch() {
        doReturn(employee).when(employeeRepository).save(any(Employee.class));

        EmployeePatchDto employeePatchDto = new EmployeePatchDto();
        Employee newEmployee = employeeDataService.patch(any(Long.class), employeePatchDto);

        assertNotNull(newEmployee);
        assertEquals(employee.getFullName(), newEmployee.getFullName());
    }

    @Test
    public void Should_NotPatchCategory_When_EmployeeNotExemptioned_AndEducationNotValid() {
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();

        EducationNotValidException exception = assertThrows(EducationNotValidException.class, () -> {
            employeeDataService.patch(any(Long.class), employeeCategoryDto);
        });

        assertEquals("Education is not valid to add category", exception.getMessage());
    }

    @Test
    public void Should_PatchCategory_When_EmployeeNotExemptioned_AndEducationIsValid() {
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setCategory(Category.FIRST);

        BeanUtils.copyProperties(employeeCategoryDto, employee);
        doReturn(employee).when(employeeRepository).save(any(Employee.class));

        doReturn(true).when(employeeValidationService).educationIsValid(any(Employee.class));

        Employee newEmployee = employeeDataService.patch(any(Long.class), employeeCategoryDto);

        assertNotNull(newEmployee);
        assertEquals(Category.FIRST, newEmployee.getCategory());
    }

    @Test
    public void Should_PatchEducation() {
        EmployeeEducationDto employeeEducationDto = new EmployeeEducationDto();
        employeeEducationDto.setEducation(Education.HIGHER);
        employeeEducationDto.setEduName("AGH");
        employeeEducationDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        BeanUtils.copyProperties(employeeEducationDto, employee);
        doReturn(employee).when(employeeRepository).save(any(Employee.class));

        Employee newEmployee = employeeDataService.patch(any(Long.class), employeeEducationDto);

        assertNotNull(newEmployee);
        assertEquals("AGH", newEmployee.getEduName());
    }
}
