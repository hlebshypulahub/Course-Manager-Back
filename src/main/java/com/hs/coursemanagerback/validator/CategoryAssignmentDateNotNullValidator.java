package com.hs.coursemanagerback.validator;

import com.hs.coursemanagerback.model.enumeration.Category;
import com.hs.coursemanagerback.model.employee.dto.EmployeeCategoryPatchDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryAssignmentDateNotNullValidator implements ConstraintValidator<CategoryAssignmentDateNotNull, EmployeeCategoryPatchDto> {

    @Override
    public void initialize(CategoryAssignmentDateNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(EmployeeCategoryPatchDto employeeCategoryPatchDto, ConstraintValidatorContext constraintValidatorContext) {
        return employeeCategoryPatchDto.getCategory() == Category.NONE || employeeCategoryPatchDto.getCategoryAssignmentDate() != null;
    }
}
