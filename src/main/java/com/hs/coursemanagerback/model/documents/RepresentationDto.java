package com.hs.coursemanagerback.model.documents;

import com.hs.coursemanagerback.model.enumeration.Category;

public class RepresentationDto extends DocumentDto {

    private String overallWorkExperience;
    private String lastPositionWorkExperience;
    private String recommendation;
    private String showing;
    private String flaws;
    private String principalCompany;
    private boolean categoryConfirmation;
    private boolean categoryAssignment;
    private Category category;
    private String qualification;

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

    public String getPrincipalCompany() {
        return principalCompany;
    }

    public void setPrincipalCompany(String principalCompany) {
        this.principalCompany = principalCompany;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
