package com.hs.coursemanagerback.validator;

import com.hs.coursemanagerback.model.employee.dto.EmployeeExemptionDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExemptionNotNullValidator implements ConstraintValidator<ExemptionNotNull, EmployeeExemptionDto> {
    @Override
    public void initialize(ExemptionNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(EmployeeExemptionDto employeeExemptionDto, ConstraintValidatorContext constraintValidatorContext) {
        if (employeeExemptionDto.getExemption() == null) {
            return employeeExemptionDto.getExemptionStartDate() == null && employeeExemptionDto.getExemptionEndDate() == null;
        } else {
            return employeeExemptionDto.getExemptionStartDate() != null;
        }
    }
}
