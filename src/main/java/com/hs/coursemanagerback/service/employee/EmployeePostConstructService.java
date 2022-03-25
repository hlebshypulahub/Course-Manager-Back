package com.hs.coursemanagerback.service.employee;

import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.service.course.CourseService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class EmployeePostConstructService {

    private final EmployeeDataService employeeDataService;
    private final CourseService courseService;
    private final EmployeeNoteService employeeNoteService;
    private final Logger logger;

    @Autowired
    public EmployeePostConstructService(EmployeeDataService employeeDataService, CourseService courseService, EmployeeNoteService employeeNoteService, Logger logger) {
        this.employeeNoteService = employeeNoteService;
        this.employeeDataService = employeeDataService;
        this.courseService = courseService;
        this.logger = logger;
    }

    /// "0 0 6 * * *" every day 6 a.m.
    /// "0 * * * * *" every minute
    @Scheduled(cron = "0 0 6 * * *")
    @PostConstruct
    public void processEmployees() {
        List<Employee> employees = employeeDataService.getAll();
        for (Employee employee : employees) {
            courseService.process(employee);
            employeeNoteService.process(employee);

            /// Saving
            employeeDataService.save(employee);
        }
    }
}
