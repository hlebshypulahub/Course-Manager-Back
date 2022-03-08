package com.hs.coursemanagerback.service.employee;

import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.model.employee.dto.EmployeeCategoryDto;
import com.hs.coursemanagerback.model.employee.dto.EmployeeEducationDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class EmployeeValidationService {

    private final Validator validator;

    @Autowired
    public EmployeeValidationService(Validator validator) {
        this.validator = validator;
    }

    public boolean categoryIsValid(Employee employee) {
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        BeanUtils.copyProperties(employee, employeeCategoryDto);
        Set<ConstraintViolation<EmployeeCategoryDto>> violations = validator.validate(employeeCategoryDto);
        return violations.isEmpty();
    }

    public boolean educationIsValid(Employee employee) {
        EmployeeEducationDto employeeEducationDto = new EmployeeEducationDto();
        BeanUtils.copyProperties(employee, employeeEducationDto);
        Set<ConstraintViolation<EmployeeEducationDto>> violations = validator.validate(employeeEducationDto);
        return violations.isEmpty();
    }
}
