package com.hs.coursemanagerback.service.course;

import com.hs.coursemanagerback.model.course.Course;
import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.repository.CourseRepository;
import com.hs.coursemanagerback.service.employee.EmployeeDataService;
import com.hs.coursemanagerback.service.employee.EmployeeValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final EmployeeValidationService employeeValidationService;
    private EmployeeDataService employeeDataService;

    @Autowired
    public CourseService(CourseRepository courseRepository, EmployeeValidationService employeeValidationService) {
        this.courseRepository = courseRepository;
        this.employeeValidationService = employeeValidationService;
    }

    @Autowired
    public void setEmployeeDataService(EmployeeDataService employeeDataService) {
        this.employeeDataService = employeeDataService;
    }

    public Course addCourseForEmployee(Long employeeId, Course course) {
        return addCourse(employeeDataService.findById(employeeId), course);
    }

    private Course addCourse(Employee employee, Course course) {
        employee.addCourse(course);
        process(employee);
        return courseRepository.save(course);
    }

    private void deleteCourse(Employee employee, Course course) {
        employee.deleteCourse(course);
        courseRepository.delete(course);
        process(employee);
        employeeDataService.save(employee);
    }

    public void process(@Valid Employee employee) {
        if (employeeValidationService.categoryIsValid(employee)) {
            employee.setCourseHoursSum(employee.getCourses().stream().filter(course ->
                    courseIsBetweenDates(course,
                            employee.getCategoryAssignmentDeadlineDate().minusYears(Employee.CATEGORY_VERIFICATION_YEARS),
                            employee.getCategoryAssignmentDeadlineDate())
            ).mapToInt(Course::getHours).sum());
        }
    }

    public List<Course> getCoursesForEmployee(Long employeeId) {
        return courseRepository.findAllByEmployeeId(employeeDataService.findById(employeeId).getId());
    }

    public boolean courseIsBetweenDates(Course course, LocalDate date1, LocalDate date2) {
        return !course.getStartDate().isBefore(date1) && course.getEndDate().isBefore(date2);
    }


    public boolean delete(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not exist: " + id));
        Employee employee = course.getEmployee();

        deleteCourse(employee, course);

        return true;
    }
}
