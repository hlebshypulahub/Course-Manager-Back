package com.hs.coursemanagerback.controller.employee;

import com.hs.coursemanagerback.model.documents.QualificationSheetDto;
import com.hs.coursemanagerback.model.documents.RepresentationDto;
import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.model.employee.dto.*;
import com.hs.coursemanagerback.model.enumeration.DocumentType;
import com.hs.coursemanagerback.service.employee.EmployeeDataService;
import com.hs.coursemanagerback.service.employee.EmployeeDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = {"Content-Disposition"})
@RequestMapping("/api/v1/employees")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class EmployeeController {

    private final EmployeeDataService employeeDataService;
    private final EmployeeDocumentService employeeDocumentService;

    @Autowired
    public EmployeeController(EmployeeDataService employeeDataService, EmployeeDocumentService employeeDocumentService) {
        this.employeeDataService = employeeDataService;
        this.employeeDocumentService = employeeDocumentService;
    }

    @GetMapping("")
    public List<Employee> getEmployees() {
        return employeeDataService.getAll();
    }

    @GetMapping("/for-course-plan")
    public List<Employee> getEmployeesForCoursePlan() {
        return employeeDataService.getAllForCoursePlan();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return ResponseEntity.ok(employeeDataService.findById(id));
    }

    @PatchMapping(path = "/{id}/education", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeEducationPatchDto employeeEducationPatchDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeEducationPatchDto));
    }

    @PatchMapping(path = "/{id}/category", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeCategoryPatchDto employeeCategoryPatchDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeCategoryPatchDto));
    }

    @PatchMapping(path = "/{id}/category-deadline", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeCategoryDeadlinePatchDto employeeCategoryDeadlinePatchDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeCategoryDeadlinePatchDto));
    }

    @PatchMapping(path = "/{id}/active", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeActivePatchDto employeeActivePatchDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeActivePatchDto));
    }

    @PatchMapping(path = "/{id}/exemption", consumes = "application/merge-patch+json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeExemptionPatchDto employeeExemptionPatchDto) {
        return ResponseEntity.ok(employeeDataService.patch(id, employeeExemptionPatchDto));
    }

    @PostMapping(value = "/{id}/documents/representation", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<byte[]> getDocument(@PathVariable(name = "id") Long employeeId, @RequestBody RepresentationDto representationDto) {
        ByteArrayInputStream bis = employeeDocumentService.generateDocument(employeeId, representationDto);

        byte[] byteArray = bis.readAllBytes();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.TEXT_HTML_VALUE));
        headers.setContentLength(byteArray.length);
        headers.add("Content-Disposition", "inline; filename=" + employeeId + "_" + DocumentType.REPRESENTATION.getLabel() + ".html");

        return new ResponseEntity<>(byteArray, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/documents/qualification-sheet", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<byte[]> getDocument(@PathVariable(name = "id") Long employeeId, @RequestBody QualificationSheetDto qualificationSheetDto) {
        ByteArrayInputStream bis = employeeDocumentService.generateDocument(employeeId, qualificationSheetDto);

        byte[] byteArray = bis.readAllBytes();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.TEXT_HTML_VALUE));
        headers.setContentLength(byteArray.length);
        headers.add("Content-Disposition", "inline; filename=document.html");

        return new ResponseEntity<>(byteArray, headers, HttpStatus.OK);
    }
}
