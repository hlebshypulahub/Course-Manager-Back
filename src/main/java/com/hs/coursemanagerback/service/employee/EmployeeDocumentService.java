package com.hs.coursemanagerback.service.employee;

import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.model.employee.dto.RepresentationDto;
import com.hs.coursemanagerback.utils.StringDocumentsUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class EmployeeDocumentService {

    private final EmployeeDataService employeeDataService;
    private final Logger logger;

    @Autowired
    public EmployeeDocumentService(EmployeeDataService employeeDataService, Logger logger) {
        this.employeeDataService = employeeDataService;
        this.logger = logger;
    }

    public ByteArrayInputStream generateDocument(Long employeeId, RepresentationDto representationDto) {

        Employee employee = employeeDataService.findById(employeeId);

        return generateRepresentation(employee, representationDto);
    }

    private ByteArrayInputStream generateRepresentation(Employee employee, RepresentationDto representationDto) {
        String html = "";

        try {
            html = Files.readString(Path.of("src/main/java/com/hs/coursemanagerback/documents/representation.html"));

            html = html.replace("POSITION", employee.getPosition())
                       .replace("COMPANY", representationDto.getCompany())
                       .replace("FULL_NAME", employee.getFullName())
                       .replace("CATEGORY", representationDto.getCategory().getRepresentationLabel())
                       .replace("ASSIGNMENT_OPEN", representationDto.isCategoryAssignment() ? "<u><b>" : "")
                       .replace("ASSIGNMENT_CLOSE", representationDto.isCategoryAssignment() ? "</b></u>" : "")
                       .replace("CONFIRMATION_OPEN", representationDto.isCategoryConfirmation() ? "<u><b>" : "")
                       .replace("CONFIRMATION_CLOSE", representationDto.isCategoryConfirmation() ? "</b></u>" : "")
                       .replace("QUALIFICATION", employee.getQualification())
                       .replace("OVERALL_WORK_EXPERIENCE", representationDto.getOverallWorkExperience())
                       .replace("LAST_POS_WORK_EXPERIENCE", representationDto.getLastPositionWorkExperience());

            String[] strArray = StringDocumentsUtils.separateForRepresentation(representationDto.getRecommendation(), 70);
            html = StringDocumentsUtils.concatLines(html, strArray, "RECOMMENDATION");

            strArray = StringDocumentsUtils.separateForRepresentation(representationDto.getShowing(), 40);
            html = StringDocumentsUtils.concatLines(html, strArray, "SHOWING");

            strArray = StringDocumentsUtils.separateForRepresentation(representationDto.getFlaws(), 30);
            html = StringDocumentsUtils.concatLines(html, strArray, "FLAWS");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(html.getBytes());
    }

}
