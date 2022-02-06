package com.hs.coursemanagerback.course;

import com.hs.coursemanagerback.model.course.Course;
import com.hs.coursemanagerback.model.employee.Employee;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/// Unit tests
public class CoursePojoTests {

    @Test
    public void When_AddCourseFromEmployeePojo_EmployeeIsSet() {
        Employee employee = new Employee();
        employee.setCourses(new HashSet<>());

        Course course = new Course();

        employee.addCourse(course);

        assertEquals(employee, course.getEmployee());
    }

}
