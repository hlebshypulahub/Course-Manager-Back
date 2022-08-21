package com.hs.coursemanagerback.model.employee.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmployeeCategoryDto.class, name = "category"),
        @JsonSubTypes.Type(value = EmployeeEducationDto.class, name = "education"),
        @JsonSubTypes.Type(value = EmployeeCategoryDeadlineDto.class, name = "categoryDeadline"),
        @JsonSubTypes.Type(value = EmployeeExemptionDto.class, name = "exemption"),
        @JsonSubTypes.Type(value = EmployeeActiveDto.class, name = "active"),
        @JsonSubTypes.Type(value = EmployeeNoteDto.class, name = "note"),
        @JsonSubTypes.Type(value = EmployeePharmacyDto.class, name = "pharmacy"),
        @JsonSubTypes.Type(value = EmployeeDobDto.class, name = "dob"),
        @JsonSubTypes.Type(value = EmployeePartTimeDto.class, name = "partTime")
})
public abstract class EmployeeDto {
}
