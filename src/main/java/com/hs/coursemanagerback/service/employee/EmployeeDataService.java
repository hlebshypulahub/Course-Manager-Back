package com.hs.coursemanagerback.service.employee;

import com.hs.coursemanagerback.exception.CategoryNotValidException;
import com.hs.coursemanagerback.exception.EducationNotValidException;
import com.hs.coursemanagerback.model.course.Course;
import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.model.employee.dto.*;
import com.hs.coursemanagerback.model.enumeration.Category;
import com.hs.coursemanagerback.model.user.User;
import com.hs.coursemanagerback.repository.EmployeeRepository;
import com.hs.coursemanagerback.repository.UserRepository;
import com.hs.coursemanagerback.service.course.CourseService;
import com.hs.coursemanagerback.service.user.PrincipleService;
import com.hs.coursemanagerback.utils.converters.StringToLocalDateConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EmployeeDataService {

    @Value("${coursemanager.app.username}")
    private String username;

    private final EmployeeRepository employeeRepository;
    private final CourseService courseService;
    private final EmployeeValidationService employeeValidationService;
    private final EmployeeExemptionService employeeExemptionService;
    private final EmployeeFilteringService employeeFilteringService;
    private final EmployeeCategoryService employeeCategoryService;
    private final PrincipleService principleService;
    private final UserRepository userRepository;
    private final EmployeeNoteService employeeNoteService;

    @Autowired
    public EmployeeDataService(EmployeeRepository employeeRepository, CourseService courseService,
                               EmployeeValidationService employeeValidationService, EmployeeExemptionService employeeExemptionService,
                               EmployeeFilteringService employeeFilteringService, EmployeeCategoryService employeeCategoryService, PrincipleService principleService,
                               UserRepository userRepository, EmployeeNoteService employeeNoteService) {
        this.employeeRepository = employeeRepository;
        this.courseService = courseService;
        this.employeeValidationService = employeeValidationService;
        this.employeeExemptionService = employeeExemptionService;
        this.employeeFilteringService = employeeFilteringService;
        this.employeeCategoryService = employeeCategoryService;
        this.principleService = principleService;
        this.userRepository = userRepository;
        this.employeeNoteService = employeeNoteService;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllForPrincipal() {
        return employeeRepository.findAllByUserId(principleService.getPrincipalUser().getId());
    }

    public List<Employee> getAllForCoursePlan() {
        return employeeFilteringService.filterForCoursePlan(getAllForPrincipal());
    }

    public Map<String, List<Employee>> getEmployeeListsByGroups() {
        List<Employee> employees = getAllForPrincipal();

        Map<String, List<Employee>> map = new HashMap<>();

        map.put("lackOfData", getAllByLackOfData(employees));
        map.put("noCoursesOrCourseDateAndLackOfHours", getAllByNoCoursesOrCourseDateAndLackOfHours(employees));
        map.put("docsDateOrEnoughHours", getAllByDocsDateOrEnoughHours(employees));
        map.put("notActiveOrExemptioned", getAllByNotActiveOrExemptioned(employees));
        map.put("notificationToday", getAllByNotificationToday(employees));

        return map;
    }

    private List<Employee> getAllByLackOfData(List<Employee> list) {
        return list.stream().filter(e -> e.getEducation() == null || e.getCategory() == null).collect(Collectors.toList());
    }

    private List<Employee> getAllByNoCoursesOrCourseDateAndLackOfHours(List<Employee> list) {
        List<Employee> employees = new ArrayList<>(list);
        employees.removeAll(getAllByLackOfData(employees));
        List<Employee> resultList = new ArrayList<>();

        Predicate<List<Course>> lackOfCourses = List::isEmpty;
        employees.stream().filter(e -> lackOfCourses.test(new ArrayList<>(e.getCourses()))).forEach(resultList::add);
        employees.removeAll(resultList);

        Predicate<Employee> lastCourseDateIsBefore3_5yearsAgo = employee -> employee.getCourses().stream().max(Comparator.comparing(Course::getStartDate)).get()
                                                                                    .getEndDate().isBefore(LocalDate.now().minusYears(3).minusMonths(6));
        Predicate<Employee> lackOfHours = employee -> {
            if (employee.getCategory() != Category.NONE) {
                return employee.getCourseHoursSum() < employee.getEducation().getRequiredHours();
            } else {
                return employee.getCourseHoursSum() < employee.getEducation().getRequiredHoursNoneCategory();
            }
        };
        employees.stream().filter(lastCourseDateIsBefore3_5yearsAgo.and(lackOfHours)).forEach(resultList::add);

        return resultList;
    }

    private List<Employee> getAllByDocsDateOrEnoughHours(List<Employee> list) {
        List<Employee> employees = new ArrayList<>(list);
        employees.removeAll(getAllByLackOfData(employees));

        Predicate<Employee> enoughHours = employee -> {
            if (employee.getCategory() != Category.NONE) {
                return employee.getCourseHoursSum() >= employee.getEducation().getRequiredHours();
            } else {
                return employee.getCourseHoursSum() >= employee.getEducation().getRequiredHoursNoneCategory();
            }
        };

        Predicate<Employee> lessThan4monthsToDocsDate = employee -> employee.getDocsSubmitDeadlineDate().isBefore(LocalDate.now().plusMonths(4));

        return employees.stream().filter(enoughHours.or(lessThan4monthsToDocsDate)).collect(Collectors.toList());
    }

    private List<Employee> getAllByNotActiveOrExemptioned(List<Employee> list) {
        return list.stream().filter(Predicate.not(Employee::isActive).or(Employee::isExemptioned)).collect(Collectors.toList());
    }

    private List<Employee> getAllByNotificationToday(List<Employee> list) {
        return list.stream().filter(e -> e.getNotificationDate() != null && e.getNotificationDate().isEqual(LocalDate.now())).collect(Collectors.toList());
    }

    public Employee findById(long id) {
        return employeeRepository.findByIdAndUserId(id, principleService.getPrincipalUser().getId()).orElseThrow(() -> new ResourceNotFoundException("Employee not exist: id = " + id));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee patch(Long id, EmployeeDto employeeDto) {
        Employee employee = findById(id);

        if (employeeDto instanceof EmployeeNoteDto || employeeDto instanceof EmployeeActiveDto || employeeDto instanceof EmployeeEducationDto) {
            patchDto(employee, employeeDto);
        } else if (employeeDto instanceof EmployeeCategoryDto) {
            patchEmployeeCategory(employee, employeeDto);
        } else if (employeeDto instanceof EmployeeCategoryDeadlineDto) {
            patchEmployeeCategoryDeadline(employee, employeeDto);
        } else if (employeeDto instanceof EmployeeExemptionDto) {
            patchEmployeeExemption(employee, employeeDto);
        }

        return save(employee);
    }

    private void patchEmployeeCategory(Employee employee, EmployeeDto employeeDto) {
        if (!employeeValidationService.educationIsValid(employee)) {
            throw new EducationNotValidException("Education is not valid to add category");
        }

        if (!employee.isExemptioned()) {
            BeanUtils.copyProperties(employeeDto, employee);
            employeeCategoryService.process(employee);
            courseService.process(employee);
        }
    }

    private void patchEmployeeCategoryDeadline(Employee employee, EmployeeDto employeeDto) {
        if (!employeeValidationService.categoryIsValid(employee)) {
            throw new CategoryNotValidException("Category is not valid to edit assignment deadline date");
        }
        if (!employee.isExemptioned()) {
            BeanUtils.copyProperties(employeeDto, employee);
            employeeCategoryService.setCategoryAssignmentDeadlineDate(employee, employee.getCategoryAssignmentDeadlineDate());
            courseService.process(employee);
        }
    }

    private void patchEmployeeExemption(Employee employee, EmployeeDto employeeDto) {
        if (!employeeValidationService.categoryIsValid(employee)) {
            throw new CategoryNotValidException("Category is not valid to add exemption");
        }
        BeanUtils.copyProperties(employeeDto, employee);
        employeeExemptionService.process(employee);
    }

    private void patchDto(Employee employee, EmployeeDto employeeDto) {
        BeanUtils.copyProperties(employeeDto, employee);
    }

    ///
    ///
    /// TODO Email notification that card is not valid
    /// in file service as well
    ///
    public void buildEmployeeFromEmployeeFromClientDto(EmployeeFromClientDto employeeFromClientDto) {

        if (employeeFromClientDtoIsValid(employeeFromClientDto)) {

            User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
            Employee employee = new Employee();

            if (employeeRepository.findByForeignId(employeeFromClientDto.getForeignId()).isPresent()) {
                employee = employeeRepository.findByForeignId(employeeFromClientDto.getForeignId()).get();
                employee.setFullName(employeeFromClientDto.getFullName());
                employee.setHiringDate(StringToLocalDateConverter.convert(employeeFromClientDto.getHiringDate()));
                employee.setJobFacility(employeeFromClientDto.getJobFacility());
                employee.setPosition(employeeFromClientDto.getPosition());
            } else {
                employee.setForeignId(employeeFromClientDto.getForeignId());
                employee.setFullName(employeeFromClientDto.getFullName());
                employee.setHiringDate(StringToLocalDateConverter.convert(employeeFromClientDto.getHiringDate()));
                employee.setJobFacility(employeeFromClientDto.getJobFacility());
                employee.setPosition(employeeFromClientDto.getPosition());
                employee.setExemptioned(false);
                employee.setActive(true);

                user.addEmployee(employee);
            }

            save(employee);
        } else {
            employeeNoteService.sendWarningEmail(employeeFromClientDto.getForeignId());
        }
    }

    private boolean employeeFromClientDtoIsValid(EmployeeFromClientDto employee) {
        return employee.getForeignId() != null && employee.getForeignId() > 0
                && employee.getFullName() != null && !employee.getFullName().isBlank()
                && employee.getHiringDate() != null
                && employee.getJobFacility() != null && !employee.getJobFacility().isBlank()
                && employee.getPosition() != null && !employee.getPosition().isBlank();
    }
}
