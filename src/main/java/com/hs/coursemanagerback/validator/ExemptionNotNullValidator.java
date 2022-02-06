package com.hs.coursemanagerback.validator;

import com.hs.coursemanagerback.model.employee.dto.EmployeeExemptionPatchDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExemptionNotNullValidator implements ConstraintValidator<ExemptionNotNull, EmployeeExemptionPatchDto> {
    @Override
    public void initialize(ExemptionNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(EmployeeExemptionPatchDto employeeExemptionPatchDto, ConstraintValidatorContext constraintValidatorContext) {
        if (employeeExemptionPatchDto.getExemption() == null) {
            return employeeExemptionPatchDto.getExemptionStartDate() == null && employeeExemptionPatchDto.getExemptionEndDate() == null;
        } else {
            return employeeExemptionPatchDto.getExemptionStartDate() != null;
        }
    }
}
