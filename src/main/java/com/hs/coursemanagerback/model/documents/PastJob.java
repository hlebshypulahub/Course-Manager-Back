package com.hs.coursemanagerback.model.documents;

public class PastJob {

    private String pastJob;
    private String startDate;
    private String endDate;

    public String getPastJob() {
        return pastJob;
    }

    public void setPastJob(String pastJob) {
        this.pastJob = pastJob;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? "" : startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? "" : endDate;
    }
}
