package com.hs.coursemanagerback.employee;

import com.hs.coursemanagerback.exception.CategoryNotValidException;
import com.hs.coursemanagerback.exception.EducationNotValidException;
import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.model.employee.dto.EmployeeCategoryDeadlineDto;
import com.hs.coursemanagerback.model.employee.dto.EmployeeCategoryDto;
import com.hs.coursemanagerback.model.employee.dto.EmployeeEducationDto;
import com.hs.coursemanagerback.model.employee.dto.EmployeeExemptionDto;
import com.hs.coursemanagerback.model.enumeration.Category;
import com.hs.coursemanagerback.model.enumeration.Education;
import com.hs.coursemanagerback.model.enumeration.Exemption;
import com.hs.coursemanagerback.service.employee.EmployeeDataService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/// Integration tests
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeDataServiceIntegrationTests {

    @Autowired
    EmployeeDataService employeeDataService;

    private Employee employee;

    @BeforeEach
    public void before() {
        employee = new Employee();
        employee.setForeignId(1L);
        employee.setFullName("Test");
        employee.setHiringDate(LocalDate.of(2020, 5, 5));
        employee.setJobFacility("Apteka 9");
        employee.setPosition("Farmaceuta");
    }

    @AfterEach
    public void after() {
        employee = null;
    }

    @Test
    public void Should_SaveEmployee() {
        Employee newEmployee = employeeDataService.save(employee);

        assertNotNull(newEmployee);
        assertNotNull(newEmployee.getId());
        assertEquals("Test", newEmployee.getFullName());
    }

    @Test
    public void Should_FindById() {
        Employee newEmployee = employeeDataService.save(employee);

        Long id = newEmployee.getId();

        Employee foundEmployee = employeeDataService.findById(id);

        assertNotNull(foundEmployee);
        assertEquals(id, foundEmployee.getId());
        assertEquals(newEmployee.getFullName(), foundEmployee.getFullName());
    }

    @Test
    public void Should_GetAll() {
        /// Actual size
        int size = employeeDataService.getAll().size();

        LocalDate date = LocalDate.now();

        /// Employee 0
        employeeDataService.save(employee);

        /// Employee 1
        Employee employee1 = new Employee();
        employee1.setForeignId(1L);
        employee1.setFullName("Test");
        employee1.setHiringDate(LocalDate.of(2020, 5, 5));
        employee1.setJobFacility("Apteka 9");
        employee1.setPosition("Farmaceuta");

        employeeDataService.save(employee1);

        /// Employee 2
        Employee employee2 = new Employee();
        employee2.setForeignId(1L);
        employee2.setFullName("Test");
        employee2.setHiringDate(LocalDate.of(2020, 5, 5));
        employee2.setJobFacility("Apteka 9");
        employee2.setPosition("Farmaceuta");

        employeeDataService.save(employee2);

        /// Get
        List<Employee> employees = employeeDataService.getAll();

        assertEquals(size + 3, employees.size());
    }

    @Test
    public void Should_GetAllForCoursePlan() {
        /// Actual size
        int size = employeeDataService.getAllForCoursePlan().size();

        LocalDate date = LocalDate.now();

        /// Employee 0
        employee.setActive(true);
        employee.setEducation(Education.HIGHER);
        employee.setEduName("AGH");
        employee.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employee.setCategory(Category.FIRST);
        employee.setCategoryNumber("285");
        employee.setQualification("Farmaceuta");
        employee.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryAssignmentDeadlineDate(date.plusYears(1).plusMonths(6));

        employeeDataService.save(employee);

        /// Employee 1
        Employee employee1 = new Employee();
        employee1.setForeignId(1L);
        employee1.setFullName("Test");
        employee1.setHiringDate(LocalDate.of(2020, 5, 5));
        employee1.setJobFacility("Apteka 9");
        employee1.setPosition("Farmaceuta");
        employee1.setActive(true);
        employee1.setEducation(Education.SECONDARY);
        employee1.setEduName("AGH");
        employee1.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employee1.setCategory(Category.FIRST);
        employee1.setCategoryNumber("285");
        employee1.setQualification("Farmaceuta");
        employee1.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employee1.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee1.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));
        employee1.setCategoryAssignmentDeadlineDate(date.plusMonths(6));

        employeeDataService.save(employee1);

        /// Employee 2
        Employee employee2 = new Employee();
        employee2.setForeignId(1L);
        employee2.setFullName("Test");
        employee2.setHiringDate(LocalDate.of(2020, 5, 5));
        employee2.setJobFacility("Apteka 9");
        employee2.setPosition("Farmaceuta");
        employee2.setActive(true);
        employee2.setEducation(Education.SECONDARY);
        employee2.setEduName("AGH");
        employee2.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employee2.setCategory(Category.FIRST);
        employee2.setCategoryNumber("285");
        employee2.setQualification("Farmaceuta");
        employee2.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employee2.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee2.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));
        employee2.setCategoryAssignmentDeadlineDate(date.plusYears(1).plusMonths(6));

        employeeDataService.save(employee2);

        /// Get
        List<Employee> employees = employeeDataService.getAllForCoursePlan();

        assertEquals(size + 2, employees.size());
    }

    @Test
    public void Should_PatchCategory_When_EmployeeNotExemptioned_AndEducationIsValid() {
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setCategory(Category.FIRST);
        employeeCategoryDto.setCategoryNumber("228");
        employeeCategoryDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryDto.setQualification("Farmaceuta");

        EmployeeEducationDto employeeEducationDto = new EmployeeEducationDto();
        employeeEducationDto.setEducation(Education.HIGHER);
        employeeEducationDto.setEduName("AGH");
        employeeEducationDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        BeanUtils.copyProperties(employeeEducationDto, employee);
        BeanUtils.copyProperties(employeeCategoryDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeCategoryDto);

        assertNotNull(newEmployee);
        assertEquals(Category.FIRST, newEmployee.getCategory());
    }

    @Test
    public void Should_PatchCategory_When_EmployeeNotExemptioned_AndEducationNotValid() {
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();

        employee = employeeDataService.save(employee);

        EducationNotValidException exception = assertThrows(EducationNotValidException.class, () -> {
            employeeDataService.patch(employee.getId(), employeeCategoryDto);
        });

        assertEquals("Education is not valid to add category", exception.getMessage());
    }

    @Test
    public void Should_NotPatchCategory_When_EmployeeIsExemptioned_AndEducationIsValid() {
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setCategory(Category.FIRST);

        EmployeeEducationDto employeeEducationDto = new EmployeeEducationDto();
        employeeEducationDto.setEducation(Education.HIGHER);
        employeeEducationDto.setEduName("AGH");
        employeeEducationDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        BeanUtils.copyProperties(employeeEducationDto, employee);

        employee.setExemptioned(true);

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeCategoryDto);

        assertNotNull(newEmployee);
        assertNull(newEmployee.getCategory());
    }

    @Test
    public void Should_PatchCategoryDeadline_When_EmployeeNotExemptioned_AndCategoryIsValid() {
        LocalDate date = LocalDate.of(2020, 5, 5);

        EmployeeCategoryDeadlineDto employeeCategoryDeadlineDto = new EmployeeCategoryDeadlineDto();
        employeeCategoryDeadlineDto.setCategoryAssignmentDeadlineDate(date);

        EmployeeEducationDto employeeEducationDto = new EmployeeEducationDto();
        employeeEducationDto.setEducation(Education.HIGHER);
        employeeEducationDto.setEduName("AGH");
        employeeEducationDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setCategory(Category.FIRST);
        employeeCategoryDto.setCategoryNumber("228");
        employeeCategoryDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryDto.setQualification("Farmaceuta");

        BeanUtils.copyProperties(employeeEducationDto, employee);
        BeanUtils.copyProperties(employeeCategoryDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeCategoryDeadlineDto);

        assertNotNull(newEmployee);
        assertEquals(date, newEmployee.getCategoryAssignmentDeadlineDate());
    }

    @Test
    public void Should_NotPatchCategoryDeadline_When_EmployeeNotExemptioned_AndCategoryNotValid() {
        LocalDate date = LocalDate.of(2020, 5, 5);

        EmployeeCategoryDeadlineDto employeeCategoryDeadlineDto = new EmployeeCategoryDeadlineDto();
        employeeCategoryDeadlineDto.setCategoryAssignmentDeadlineDate(date);

        EmployeeEducationDto employeeEducationDto = new EmployeeEducationDto();
        employeeEducationDto.setEducation(Education.HIGHER);
        employeeEducationDto.setEduName("AGH");
        employeeEducationDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setCategory(Category.FIRST);
//        employeeCategoryPatchDto.setCategoryNumber("228");
        employeeCategoryDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryDto.setQualification("Farmaceuta");

        BeanUtils.copyProperties(employeeEducationDto, employee);
        BeanUtils.copyProperties(employeeCategoryDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        CategoryNotValidException exception = assertThrows(CategoryNotValidException.class, () -> {
            employeeDataService.patch(employee.getId(), employeeCategoryDeadlineDto);
        });

        assertEquals("Category is not valid to edit assignment deadline date", exception.getMessage());
    }

    @Test
    public void Should_PatchExemptionWithEndDateIsNull_When_CategoryIsValid() {
        EmployeeExemptionDto employeeExemptionDto = new EmployeeExemptionDto();
        employeeExemptionDto.setExemption(Exemption.LESS_THAN_YEAR_WORK);
        employeeExemptionDto.setExemptionStartDate(LocalDate.of(2020, 5, 5));

        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setCategory(Category.FIRST);
        employeeCategoryDto.setCategoryNumber("228");
        employeeCategoryDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryDto.setQualification("Farmaceuta");

        BeanUtils.copyProperties(employeeCategoryDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeExemptionDto);

        assertNotNull(newEmployee);
        assertEquals(Exemption.LESS_THAN_YEAR_WORK, newEmployee.getExemption());
    }

    @Test
    public void Should_PatchExemptionWithEndDateNotNull_When_CategoryIsValid() {
        EmployeeExemptionDto employeeExemptionDto = new EmployeeExemptionDto();
        employeeExemptionDto.setExemption(Exemption.LESS_THAN_YEAR_WORK);
        employeeExemptionDto.setExemptionStartDate(LocalDate.of(2020, 5, 5));
        employeeExemptionDto.setExemptionEndDate(LocalDate.of(2021, 5, 5));

        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setCategory(Category.FIRST);
        employeeCategoryDto.setCategoryNumber("228");
        employeeCategoryDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryDto.setQualification("Farmaceuta");

        BeanUtils.copyProperties(employeeCategoryDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeExemptionDto);

        assertNotNull(newEmployee);
        assertNull(newEmployee.getExemption());
    }

    @Test
    public void Should_NotPatchExemptionWithEndDateNotNull_When_CategoryNotValid() {
        EmployeeExemptionDto employeeExemptionDto = new EmployeeExemptionDto();
        employeeExemptionDto.setExemption(Exemption.LESS_THAN_YEAR_WORK);
        employeeExemptionDto.setExemptionStartDate(LocalDate.of(2020, 5, 5));
        employeeExemptionDto.setExemptionEndDate(LocalDate.of(2021, 5, 5));

        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setCategory(Category.FIRST);
//        employeeCategoryPatchDto.setCategoryNumber("228");
        employeeCategoryDto.setCategoryAssignmentDate(LocalDate.of(2020, 5, 5));
        employeeCategoryDto.setQualification("Farmaceuta");

        BeanUtils.copyProperties(employeeCategoryDto, employee);

        employee.setCategoryAssignmentDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setDocsSubmitDeadlineDate(LocalDate.of(2020, 5, 5));
        employee.setCategoryPossiblePromotionDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        CategoryNotValidException exception = assertThrows(CategoryNotValidException.class, () -> {
            employeeDataService.patch(employee.getId(), employeeExemptionDto);
        });

        assertEquals("Category is not valid to add exemption", exception.getMessage());
    }

    @Test
    public void Should_PatchEducation() {
        EmployeeEducationDto employeeEducationDto = new EmployeeEducationDto();
        employeeEducationDto.setEducation(Education.HIGHER);
        employeeEducationDto.setEduName("AGH");
        employeeEducationDto.setEduGraduationDate(LocalDate.of(2020, 5, 5));

        employee = employeeDataService.save(employee);

        Employee newEmployee = employeeDataService.patch(employee.getId(), employeeEducationDto);

        assertNotNull(newEmployee);
        assertEquals(Education.HIGHER, newEmployee.getEducation());
    }
}
