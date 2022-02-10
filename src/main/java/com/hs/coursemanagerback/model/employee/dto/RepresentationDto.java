package com.hs.coursemanagerback.model.employee.dto;

import com.hs.coursemanagerback.model.enumeration.Category;

public class RepresentationDto {

    private String overallWorkExperience;
    private String lastPositionWorkExperience;
    private String recommendation;
    private String showing;
    private String flaws;
    private String company;
    private boolean categoryConfirmation;
    private boolean categoryAssignment;
    private Category category;

    public String getOverallWorkExperience() {
        return overallWorkExperience;
    }

    public void setOverallWorkExperience(String overallWorkExperience) {
        this.overallWorkExperience = overallWorkExperience;
    }

    public String getLastPositionWorkExperience() {
        return lastPositionWorkExperience;
    }

    public void setLastPositionWorkExperience(String lastPositionWorkExperience) {
        this.lastPositionWorkExperience = lastPositionWorkExperience;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public boolean isCategoryConfirmation() {
        return categoryConfirmation;
    }

    public void setCategoryConfirmation(boolean categoryConfirmation) {
        this.categoryConfirmation = categoryConfirmation;
    }

    public boolean isCategoryAssignment() {
        return categoryAssignment;
    }

    public void setCategoryAssignment(boolean categoryAssignment) {
        this.categoryAssignment = categoryAssignment;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }

    public String getFlaws() {
        return flaws;
    }

    public void setFlaws(String flaws) {
        this.flaws = flaws;
    }
}
