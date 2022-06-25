package com.hs.coursemanagerback.model.employee.dto;

public class EmployeeFromClientDto {

    private Long foreignId;
    private String fullName;
    private String hiringDate;
    private String jobFacility;
    private String position;

    public Long getForeignId() {
        return foreignId;
    }

    public void setForeignId(Long foreignId) {
        this.foreignId = foreignId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(String hiringDate) {
        this.hiringDate = hiringDate;
    }

    public String getJobFacility() {
        return jobFacility;
    }

    public void setJobFacility(String jobFacility) {
        this.jobFacility = jobFacility;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
