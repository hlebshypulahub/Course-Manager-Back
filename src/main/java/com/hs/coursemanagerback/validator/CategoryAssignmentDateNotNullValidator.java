package com.hs.coursemanagerback.validator;

import com.hs.coursemanagerback.model.enumeration.Category;
import com.hs.coursemanagerback.model.employee.dto.EmployeeCategoryDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryAssignmentDateNotNullValidator implements ConstraintValidator<CategoryAssignmentDateNotNull, EmployeeCategoryDto> {

    @Override
    public void initialize(CategoryAssignmentDateNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(EmployeeCategoryDto employeeCategoryDto, ConstraintValidatorContext constraintValidatorContext) {
        return employeeCategoryDto.getCategory() == Category.NONE || employeeCategoryDto.getCategoryAssignmentDate() != null;
    }
}
