package com.hs.coursemanagerback.model.employee.dto;

import javax.validation.constraints.NotNull;

public class EmployeeActiveDto implements EmployeeDto {

    @NotNull(message = "active cannot be null")
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
