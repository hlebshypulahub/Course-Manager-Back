package com.hs.coursemanagerback.service.employee;

import com.hs.coursemanagerback.model.documents.PastJob;
import com.hs.coursemanagerback.model.documents.QualificationSheetDto;
import com.hs.coursemanagerback.model.documents.RepresentationDto;
import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.utils.StringDocumentsUtils;
import com.hs.coursemanagerback.utils.converters.LocalDateToStringConverter;
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

    public ByteArrayInputStream generateDocument(Long employeeId, QualificationSheetDto qualificationSheetDto) {

        Employee employee = employeeDataService.findById(employeeId);

        return generateQualificationSheet(employee, qualificationSheetDto);
    }

    private ByteArrayInputStream generateQualificationSheet(Employee employee, QualificationSheetDto qualificationSheetDto) {
        String html = "";

        try {
            html = Files.readString(Path.of("src/main/java/com/hs/coursemanagerback/documents/qualification_sheet.html"));

            html = html.replace("FULL_NAME", employee.getFullName())
                       .replace("POSITION_AND_PRINCIPAL_COMPANY", qualificationSheetDto.getPositionAndPrincipalCompany())
                       .replace("DOB", qualificationSheetDto.getDob())
                       .replace("GRADUATION_YEAR", String.valueOf(employee.getEduGraduationDate().getYear()))
                       .replace("EDU_NAME", employee.getEduName())
                       .replace("DIPLOMA_NUMBER", qualificationSheetDto.getDiplomaNumber())
                       .replace("DIPLOMA_QUALIFICATION", qualificationSheetDto.getDiplomaQualification())
                       .replace("ACADEMIC_DEGREE", qualificationSheetDto.getAcademicDegree())
                       .replace("ACADEMIC_TITLE", qualificationSheetDto.getAcademicTitle())
                       .replace("HONORARY_TITLE", qualificationSheetDto.getHonoraryTitle())
                       .replace("LANGUAGE", qualificationSheetDto.getLanguage())
                       .replace("THESISES", qualificationSheetDto.getThesises())
                       .replace("CATEGORY", qualificationSheetDto.getCategory().getQualificationSheetLabel())
                       .replace("QUALIFICATION", qualificationSheetDto.getQualification())
                       .replace("CAT_ASSIGNMENT_DATE", LocalDateToStringConverter.convert(employee.getCategoryAssignmentDate()));

            StringBuilder professionalTraining = new StringBuilder();
            String[] strArray = StringDocumentsUtils.separate(qualificationSheetDto.getProfessionalTraining());
            for (String s : strArray) {
                professionalTraining.append("<div style=\"display: table\">\n" +
                        "        <div style=\"display: table-cell\">&nbsp;&nbsp;&nbsp;</div>\n" +
                        "        <div style=\"display: table-cell\">\n" +
                        "            <u>\n" +
                        "                <pre class=\"line font-set\">&nbsp; " + s + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              </pre>\n" +
                        "            </u>&nbsp;\n" +
                        "        </div>\n" +
                        "    </div>");
            }
            html = html.replace("PROFESSIONAL_TRAINING", professionalTraining.toString());

            strArray = StringDocumentsUtils.separate(qualificationSheetDto.getInventions(), 30);
            if (strArray.length > 1) {
                html = html.replace("INVENTIONS1", strArray[0]);
                StringBuilder inventions = new StringBuilder();
                for (int i = 1; i < strArray.length; i++) {
                    inventions.append("<div style=\"display: table\">\n" +
                            "        <div style=\"display: table-cell\">&nbsp;&nbsp;&nbsp;</div>\n" +
                            "        <div style=\"display: table-cell\">\n" +
                            "            <u>\n" +
                            "                <pre class=\"line font-set\">&nbsp; " + strArray[i] + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              </pre>\n" +
                            "            </u>&nbsp;\n" +
                            "        </div>\n" +
                            "    </div>");
                }
                html = html.replace("INVENTIONS2", inventions + "<div class=\"space\"></div>");
            } else {
                html = html.replace("INVENTIONS1", qualificationSheetDto.getInventions())
                           .replace("INVENTIONS2", "");
            }

            strArray = StringDocumentsUtils.separate(qualificationSheetDto.getClubs(), 50);
            if (strArray.length > 1) {
                html = html.replace("CLUBS1", strArray[0]);
                StringBuilder clubs = new StringBuilder();
                for (int i = 1; i < strArray.length; i++) {
                    clubs.append("<div style=\"display: table\">\n" +
                            "        <div style=\"display: table-cell\">&nbsp;&nbsp;&nbsp;</div>\n" +
                            "        <div style=\"display: table-cell\">\n" +
                            "            <u>\n" +
                            "                <pre class=\"line font-set\">&nbsp; " + strArray[i] + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              </pre>\n" +
                            "            </u>&nbsp;\n" +
                            "        </div>\n" +
                            "    </div>");
                }
                html = html.replace("CLUBS2", clubs + "<div class=\"space\"></div>");
            } else {
                html = html.replace("CLUBS1", qualificationSheetDto.getClubs())
                           .replace("CLUBS2", "");
            }

            StringBuilder pastJobs = new StringBuilder();
            for (PastJob pastJob : qualificationSheetDto.getPastJobs()) {
                StringBuilder pastJobSb = new StringBuilder();
                strArray = StringDocumentsUtils.separate(pastJob.getPastJob());
                for (String s : strArray) {
                    pastJobSb.append("<div style=\"display: table\">\n" +
                            "        <div style=\"display: table-cell\">&nbsp;&nbsp;&nbsp;</div>\n" +
                            "        <div style=\"display: table-cell\">\n" +
                            "            <u>\n" +
                            "                <pre class=\"line font-set\">&nbsp; " + s + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             </pre>\n" +
                            "            </u>&nbsp;\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "    <div class=\"small\">(должность служащего, организация, индивидуальный предприниматель)</div>");
                }
                pastJobs.append(pastJobSb);

                pastJobs.append(
                        "    <div style=\"display: table\">\n" +
                                "        <div style=\"display: table-cell\">c&nbsp;</div>\n" +
                                "        <div style=\"display: table-cell\">\n" +
                                "            <u>\n" +
                                "                <div class=\"my-overflow font-set\" style=\"width: 500px\">&nbsp;&nbsp;&nbsp;&nbsp; " + pastJob.getStartDate() + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>\n" +
                                "            </u></div>\n" +
                                "        <div style=\"display: table-cell\">&nbsp;&nbsp;&nbsp;по&nbsp;</div>\n" +
                                "        <div style=\"display: table-cell\">\n" +
                                "            <u>\n" +
                                "                <div class=\"my-overflow font-set\" style=\"width: 550px\">&nbsp;&nbsp;&nbsp;&nbsp; " + pastJob.getEndDate() + "\n" +
                                "                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                                "                </div>\n" +
                                "            </u></div>\n" +
                                "    </div>\n" +
                                "\n" +
                                "    <div class=\"space\"></div>");
            }
            html = html.replace("PAST_JOBS", pastJobs.toString());

//                    PAST_JOB + DATES
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(html.getBytes());
    }

    private ByteArrayInputStream generateRepresentation(Employee employee, RepresentationDto representationDto) {
        String html = "";

        try {
            html = Files.readString(Path.of("src/main/java/com/hs/coursemanagerback/documents/representation.html"));

            html = html.replace("POSITION", employee.getPosition())
                       .replace("COMPANY", representationDto.getPrincipalCompany())
                       .replace("FULL_NAME", employee.getFullName())
                       .replace("CATEGORY", representationDto.getCategory().getRepresentationLabel())
                       .replace("ASSIGNMENT_OPEN", representationDto.isCategoryAssignment() ? "<u><b>" : "")
                       .replace("ASSIGNMENT_CLOSE", representationDto.isCategoryAssignment() ? "</b></u>" : "")
                       .replace("CONFIRMATION_OPEN", representationDto.isCategoryConfirmation() ? "<u><b>" : "")
                       .replace("CONFIRMATION_CLOSE", representationDto.isCategoryConfirmation() ? "</b></u>" : "")
                       .replace("QUALIFICATION", representationDto.getQualification())
                       .replace("OVERALL_WORK_EXPERIENCE", representationDto.getOverallWorkExperience())
                       .replace("LAST_POS_WORK_EXPERIENCE", representationDto.getLastPositionWorkExperience());

            String[] strArray = StringDocumentsUtils.separate(representationDto.getRecommendation(), 70);
            html = StringDocumentsUtils.concatLines(html, strArray, "RECOMMENDATION");

            strArray = StringDocumentsUtils.separate(representationDto.getShowing(), 40);
            html = StringDocumentsUtils.concatLines(html, strArray, "SHOWING");

            strArray = StringDocumentsUtils.separate(representationDto.getFlaws(), 30);
            html = StringDocumentsUtils.concatLines(html, strArray, "FLAWS");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(html.getBytes());
    }

}
