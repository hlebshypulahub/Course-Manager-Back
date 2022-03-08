package com.hs.coursemanagerback.model.documents;

import com.hs.coursemanagerback.model.enumeration.Category;

import java.util.List;

public class QualificationSheetDto extends DocumentDto {

    private Category category;
    private String qualification;
    private String dob;
    private String diplomaNumber;
    private String diplomaQualification;
    private List<PastJob> pastJobs;
    private String professionalTraining;
    private String academicDegree;
    private String academicTitle;
    private String honoraryTitle;
    private String language;
    private String clubs;
    private String thesises;
    private String inventions;
    private String positionAndPrincipalCompany;

    @Override
    public String toString() {
        return "QualificationSheetDto{" +
                "category=" + category +
                ", qualification='" + qualification + '\'' +
                ", dob=" + dob +
                ", diplomaNumber='" + diplomaNumber + '\'' +
                ", diplomaQualification='" + diplomaQualification + '\'' +
                ", pastJobs=" + pastJobs +
                ", professionalTraining='" + professionalTraining + '\'' +
                ", academicDegree='" + academicDegree + '\'' +
                ", academicTitle='" + academicTitle + '\'' +
                ", honoraryTitle='" + honoraryTitle + '\'' +
                ", language='" + language + '\'' +
                ", clubs='" + clubs + '\'' +
                ", thesises='" + thesises + '\'' +
                ", inventions='" + inventions + '\'' +
                ", positionAndPrincipalCompany='" + positionAndPrincipalCompany + '\'' +
                '}';
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDob() {
        return dob == null ? "" : dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDiplomaNumber() {
        return diplomaNumber;
    }

    public void setDiplomaNumber(String diplomaNumber) {
        this.diplomaNumber = diplomaNumber;
    }

    public String getDiplomaQualification() {
        return diplomaQualification;
    }

    public void setDiplomaQualification(String diplomaQualification) {
        this.diplomaQualification = diplomaQualification;
    }

    public List<PastJob> getPastJobs() {
        return pastJobs;
    }

    public void setPastJobs(List<PastJob> pastJobs) {
        this.pastJobs = pastJobs;
    }

    public String getProfessionalTraining() {
        return professionalTraining;
    }

    public void setProfessionalTraining(String professionalTraining) {
        this.professionalTraining = professionalTraining;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getHonoraryTitle() {
        return honoraryTitle;
    }

    public void setHonoraryTitle(String honoraryTitle) {
        this.honoraryTitle = honoraryTitle;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getClubs() {
        return clubs;
    }

    public void setClubs(String clubs) {
        this.clubs = clubs;
    }

    public String getThesises() {
        return thesises;
    }

    public void setThesises(String thesises) {
        this.thesises = thesises;
    }

    public String getInventions() {
        return inventions;
    }

    public void setInventions(String inventions) {
        this.inventions = inventions;
    }

    public String getPositionAndPrincipalCompany() {
        return positionAndPrincipalCompany;
    }

    public void setPositionAndPrincipalCompany(String positionAndPrincipalCompany) {
        this.positionAndPrincipalCompany = positionAndPrincipalCompany;
    }
}
