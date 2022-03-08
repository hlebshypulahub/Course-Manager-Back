package com.hs.coursemanagerback.model.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class EmployeeNoteDto extends EmployeePatchDto{

    private String note;
    private boolean shouldExtendNotification;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate notificationDate;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isShouldExtendNotification() {
        return shouldExtendNotification;
    }

    public void setShouldExtendNotification(boolean shouldExtendNotification) {
        this.shouldExtendNotification = shouldExtendNotification;
    }

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDate notificationDate) {
        this.notificationDate = notificationDate;
    }
}
