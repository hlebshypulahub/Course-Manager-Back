package com.hs.coursemanagerback.service.employee;

import com.hs.coursemanagerback.exception.CategoryNotValidException;
import com.hs.coursemanagerback.exception.EducationNotValidException;
import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.model.employee.dto.*;
import com.hs.coursemanagerback.repository.EmployeeRepository;
import com.hs.coursemanagerback.service.course.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDataService {

    private final EmployeeRepository employeeRepository;
    private final CourseService courseService;
    private final EmployeeValidationService employeeValidationService;
    private final EmployeeExemptionService employeeExemptionService;
    private final EmployeeFilteringService employeeFilteringService;
    private final EmployeeCategoryService employeeCategoryService;

    @Autowired
    public EmployeeDataService(EmployeeRepository employeeRepository, CourseService courseService,
                               EmployeeValidationService employeeValidationService, EmployeeExemptionService employeeExemptionService,
                               EmployeeFilteringService employeeFilteringService, EmployeeCategoryService employeeCategoryService) {
        this.employeeRepository = employeeRepository;
        this.courseService = courseService;
        this.employeeValidationService = employeeValidationService;
        this.employeeExemptionService = employeeExemptionService;
        this.employeeFilteringService = employeeFilteringService;
        this.employeeCategoryService = employeeCategoryService;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllForCoursePlan() {
        return employeeFilteringService.filterForCoursePlan(employeeRepository.findAll());
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee patch(Long id, EmployeeDto employeeDto) {
        Employee employee = findById(id);
        patch(employee, employeeDto);
        return save(employee);
    }

    private void patch(Employee employee, EmployeeDto employeeDto) {
        if (!employee.isExemptioned()) {
            if (employeeDto instanceof EmployeeCategoryDto) {
                patchCategory(employee, employeeDto);
            } else if (employeeDto instanceof EmployeeCategoryDeadlineDto) {
                patchCategoryDeadline(employee, employeeDto);
            }
        }

        if (employeeDto instanceof EmployeeExemptionDto) {
            patchEmployeeExemption(employee, employeeDto);
        } else if (employeeDto instanceof EmployeeEducationDto) {
            patchEmployeeEducation(employee, employeeDto);
        } else if (employeeDto instanceof EmployeeActiveDto) {
            patchEmployeeActive(employee, employeeDto);
        } else if (employeeDto instanceof EmployeeNoteDto) {
            patchEmployeeNote(employee, employeeDto);
        }
    }

    private void patchCategory(Employee employee, EmployeeDto employeeDto) {
        if (!employeeValidationService.educationIsValid(employee)) {
            throw new EducationNotValidException("Education is not valid to add category");
        }
        BeanUtils.copyProperties(employeeDto, employee);
        employeeCategoryService.process(employee);
        courseService.process(employee);
    }

    private void patchCategoryDeadline(Employee employee, EmployeeDto employeeDto) {
        if (!employeeValidationService.categoryIsValid(employee)) {
            throw new CategoryNotValidException("Category is not valid to edit assignment deadline date");
        }
        BeanUtils.copyProperties(employeeDto, employee);
        employeeCategoryService.setCategoryAssignmentDeadlineDate(employee, employee.getCategoryAssignmentDeadlineDate());
        courseService.process(employee);
    }

    private void patchEmployeeExemption(Employee employee, EmployeeDto employeeDto) {
        if (!employeeValidationService.categoryIsValid(employee)) {
            throw new CategoryNotValidException("Category is not valid to add exemption");
        }
        BeanUtils.copyProperties(employeeDto, employee);
        employeeExemptionService.process(employee);
    }

    private void patchEmployeeEducation(Employee employee, EmployeeDto employeeDto) {
        BeanUtils.copyProperties(employeeDto, employee);
    }

    private void patchEmployeeNote(Employee employee, EmployeeDto employeeDto) {
        BeanUtils.copyProperties(employeeDto, employee);
    }

    private void patchEmployeeActive(Employee employee, EmployeeDto employeeDto) {
        BeanUtils.copyProperties(employeeDto, employee);
        employeeExemptionService.process(employee);
    }
}
