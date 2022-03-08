package com.hs.coursemanagerback.model.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hs.coursemanagerback.model.enumeration.Exemption;
import com.hs.coursemanagerback.validator.ExemptionNotNull;

import java.time.LocalDate;

@ExemptionNotNull
public class EmployeeExemptionDto extends EmployeePatchDto {

    private Exemption exemption;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate exemptionStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate exemptionEndDate;

    public Exemption getExemption() {
        return exemption;
    }

    public void setExemption(Exemption exemption) {
        this.exemption = exemption;
    }

    public LocalDate getExemptionStartDate() {
        return exemptionStartDate;
    }

    public void setExemptionStartDate(LocalDate exemptionStartDate) {
        this.exemptionStartDate = exemptionStartDate;
    }

    public LocalDate getExemptionEndDate() {
        return exemptionEndDate;
    }

    public void setExemptionEndDate(LocalDate exemptionEndDate) {
        this.exemptionEndDate = exemptionEndDate;
    }
}
