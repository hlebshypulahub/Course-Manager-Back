package com.hs.coursemanagerback.validator;

import com.hs.coursemanagerback.model.enumeration.Category;
import com.hs.coursemanagerback.model.employee.dto.EmployeeCategoryDto;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryNumberNotBlankValidator implements ConstraintValidator<CategoryNumberNotBlank, EmployeeCategoryDto> {
    @Override
    public void initialize(CategoryNumberNotBlank constraintAnnotation) {

    }

    @Override
    public boolean isValid(EmployeeCategoryDto employeeCategoryDto, ConstraintValidatorContext constraintValidatorContext) {
        return employeeCategoryDto.getCategory() == Category.NONE || StringUtils.isNotBlank(employeeCategoryDto.getCategoryNumber());
    }
}
