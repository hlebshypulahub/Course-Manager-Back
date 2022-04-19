package com.hs.coursemanagerback.controller.employee;

import com.hs.coursemanagerback.model.documents.ProfessionalReportDto;
import com.hs.coursemanagerback.model.documents.QualificationSheetDto;
import com.hs.coursemanagerback.model.documents.RepresentationDto;
import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.model.employee.dto.EmployeeDto;
import com.hs.coursemanagerback.service.employee.EmployeeDataService;
import com.hs.coursemanagerback.service.employee.EmployeeDocumentService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(exposedHeaders = {"Content-Disposition"})
@RequestMapping("/api/v1/employees")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class EmployeeController {

    private final EmployeeDataService employeeDataService;
    private final EmployeeDocumentService employeeDocumentService;

    @Autowired
    Logger logger;

    @Autowired
    public EmployeeController(EmployeeDataService employeeDataService, EmployeeDocumentService employeeDocumentService) {
        this.employeeDataService = employeeDataService;
        this.employeeDocumentService = employeeDocumentService;
    }

    @GetMapping("")
    public List<Employee> getEmployees() {
        return employeeDataService.getAllForPrincipal();
    }

    @GetMapping("/by-groups")
    public Map<String, List<Employee>> getEmployeesByGroups() {
        return employeeDataService.getEmployeeListsByGroups();
    }

    @GetMapping("/for-course-plan")
    public List<Employee> getEmployeesForCoursePlan() {
        return employeeDataService.getAllForCoursePlan();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return ResponseEntity.ok(employeeDataService.findById(id));
    }

    @PostMapping(path = "/{id}/patch", consumes = "application/json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeDto));
    }

    @PostMapping(value = "/{id}/documents/representation", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getRepresentation(@PathVariable(name = "id") Long employeeId, @RequestBody RepresentationDto representationDto) {
        return ResponseEntity.ok(employeeDocumentService.generateDocument(employeeId, representationDto));
    }

    @PostMapping(value = "/{id}/documents/qualification-sheet", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getQualificationSheet(@PathVariable(name = "id") Long employeeId, @RequestBody QualificationSheetDto qualificationSheetDto) {
        return ResponseEntity.ok(employeeDocumentService.generateDocument(employeeId, qualificationSheetDto));
    }

    @PostMapping(value = "/{id}/documents/professional-report", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getProfessionalReport(@PathVariable(name = "id") Long employeeId, @RequestBody ProfessionalReportDto professionalReportDto) {
        return ResponseEntity.ok(employeeDocumentService.generateDocument(employeeId, professionalReportDto));
    }

    @PostMapping(value = "/course-plan")
    public ResponseEntity<String> getCoursePlan(@RequestBody List<List<Long>> employeesIds) {
        return ResponseEntity.ok(employeeDocumentService.generateCoursePlan(employeesIds));
    }
}
