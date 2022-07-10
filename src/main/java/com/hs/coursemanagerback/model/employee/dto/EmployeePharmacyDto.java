package com.hs.coursemanagerback.model.employee.dto;

import javax.validation.constraints.NotNull;

public class EmployeePharmacyDto extends EmployeeDto {

    @NotNull(message = "pharmacy cannot be null")
    private Boolean pharmacy;

    public Boolean getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Boolean pharmacy) {
        this.pharmacy = pharmacy;
    }
}
