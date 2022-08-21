package com.hs.coursemanagerback.model.employee.dto;

public class EmployeePartTimeDto extends EmployeeDto {

    private Boolean partTime;

    public Boolean getPartTime() {
        return partTime;
    }

    public void setPartTime(Boolean partTime) {
        this.partTime = partTime;
    }
}
