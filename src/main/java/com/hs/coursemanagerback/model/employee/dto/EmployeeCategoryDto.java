package com.hs.coursemanagerback.model.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hs.coursemanagerback.model.enumeration.Category;
import com.hs.coursemanagerback.validator.CategoryAssignmentDateNotNull;
import com.hs.coursemanagerback.validator.CategoryNumberNotBlank;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@CategoryAssignmentDateNotNull
@CategoryNumberNotBlank
public class EmployeeCategoryDto extends EmployeeDto {

    @NotBlank(message = "qualification cannot be blank")
    private String qualification;
    @NotNull(message = "category cannot be null")
    private Category category;
    private String categoryNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate categoryAssignmentDate;

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public LocalDate getCategoryAssignmentDate() {
        return categoryAssignmentDate;
    }

    public void setCategoryAssignmentDate(LocalDate categoryAssignmentDate) {
        this.categoryAssignmentDate = categoryAssignmentDate;
    }
}
