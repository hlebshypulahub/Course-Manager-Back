package com.hs.coursemanagerback.service.employee;

import com.hs.coursemanagerback.model.employee.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmployeeNoteService {

    public void extendDate(Employee employee) {
        if (employee.isShouldExtendNotification() && employee.getNotificationDate() != null && employee.getNotificationDate().isEqual(LocalDate.now())) {
            employee.setNotificationDate(LocalDate.now().plusDays(1));
        }
    }

}
