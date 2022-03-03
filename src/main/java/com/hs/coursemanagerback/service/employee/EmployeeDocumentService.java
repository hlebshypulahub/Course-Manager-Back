package com.hs.coursemanagerback.service.employee;

import com.hs.coursemanagerback.model.documents.PastJob;
import com.hs.coursemanagerback.model.documents.ProfessionalReportDto;
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

    public ByteArrayInputStream generateDocument(Long employeeId, ProfessionalReportDto professionalReportDto) {

        Employee employee = employeeDataService.findById(employeeId);

        return generateProfessionalReport(employee, professionalReportDto);
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

            html = StringDocumentsUtils.replace(html, "POSITION_AND_PRINCIPAL_COMPANY", qualificationSheetDto.getPositionAndPrincipalCompany(), 100);
            html = StringDocumentsUtils.replace(html, "PROFESSIONAL_TRAINING", qualificationSheetDto.getProfessionalTraining());
            html = StringDocumentsUtils.replace(html, "CLUBS", qualificationSheetDto.getClubs(), 50);
            html = StringDocumentsUtils.replace(html, "INVENTIONS", qualificationSheetDto.getInventions(), 30);
            
            StringBuilder pastJobs = new StringBuilder();
            for (PastJob pastJob : qualificationSheetDto.getPastJobs()) {
                StringBuilder pastJobSb = new StringBuilder();
                String []strArray = StringDocumentsUtils.separate(pastJob.getPastJob());
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(html.getBytes());
    }

    private ByteArrayInputStream generateProfessionalReport(Employee employee, ProfessionalReportDto professionalReportDto) {
        String html = "";

        try {
            html = Files.readString(Path.of("src/main/java/com/hs/coursemanagerback/documents/professional_report.html"));

            html = html.replace("START_YEAR", professionalReportDto.getStartYear())
                    .replace("END_YEAR", professionalReportDto.getEndYear());

            String[] strArray = StringDocumentsUtils.separate(professionalReportDto.getMainInfo());
            StringBuilder mi = new StringBuilder("");
            if (strArray.length == 1) {
                mi.append("<div>\n" +
                        "            <u>\n" +
                        "                <pre class=\"line font-set\">&nbsp;&nbsp;&nbsp;&nbsp; " + strArray[0] + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               </pre>\n" +
                        "            </u>&nbsp;\n" +
                        "        </div>\n" +
                        "        <div class=\"small\">(указываются фамилия, собственное имя, отчество (если таковое имеется), должность служащего,</div>\n" +
                        "        <div class=\"small\" style=\"margin-top: -2px\">организация, индивидуальный предприниматель, где работает данный работник)</div>");

                html = html.replace("MAIN_INFO", mi + "<div class=\"space\"></div>");
            } else if (strArray.length > 1) {
                mi.append("<div>\n" +
                        "            <u>\n" +
                        "                <pre class=\"line font-set\">&nbsp;&nbsp;&nbsp;&nbsp; " + strArray[0] + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 </pre>\n" +
                        "            </u>&nbsp;\n" +
                        "        </div>\n" +
                        "        <div class=\"small\">(указываются фамилия, собственное имя, отчество (если таковое имеется), должность служащего,</div>\n" +
                        "        <div>\n" +
                        "            <u>\n" +
                        "                <pre class=\"line font-set\">&nbsp;&nbsp;&nbsp;&nbsp; " + strArray[1] + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               </pre>\n" +
                        "            </u>&nbsp;\n" +
                        "        </div>\n" +
                        "        <div class=\"small\">организация, индивидуальный предприниматель, где работает данный работник)</div>");

                if (strArray.length > 2) {
                    for (int i = 2; i < strArray.length; i++) {
                        mi.append("<div>\n" +
                                "        <u>\n" +
                                "            <pre class=\"line font-set\">&nbsp;&nbsp;&nbsp;&nbsp; " + strArray[i] + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           </pre>\n" +
                                "        </u>&nbsp;\n" +
                                "    </div>");
                    }
                }

                html = html.replace("MAIN_INFO", mi + "<div class=\"space\"></div>");
            } else {
                html = html.replace("MAIN_INFO", mi.toString());
            }

            strArray = StringDocumentsUtils.separate(professionalReportDto.getText());
            StringBuilder text = new StringBuilder("");
            for (int i = 0; i < strArray.length; i++) {
                html = html.replace("TEXT" + (i + 1), strArray[i]);
            }
            for (int i = strArray.length; i < 6; i++) {
                html = html.replace("TEXT" + (i + 1), "");
            }
            if (strArray.length > 6) {
                for (int i = 6; i < strArray.length; i++) {
                    text.append("<div>\n" +
                            "        <u>\n" +
                            "            <pre class=\"line font-set\">&nbsp;&nbsp;&nbsp;&nbsp; " + strArray[i] + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             </pre>\n" +
                            "        </u>&nbsp;\n" +
                            "    </div>");
                }
                html = html.replace("TEXT", text + "<div class=\"space\"></div>");
            } else {
                html = html.replace("TEXT", "");
            }

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
                       .replace("FULL_NAME", employee.getFullName())
                       .replace("CATEGORY", representationDto.getCategory().getRepresentationLabel())
                       .replace("ASSIGNMENT_OPEN", representationDto.isCategoryAssignment() ? "<u><b>" : "")
                       .replace("ASSIGNMENT_CLOSE", representationDto.isCategoryAssignment() ? "</b></u>" : "")
                       .replace("CONFIRMATION_OPEN", representationDto.isCategoryConfirmation() ? "<u><b>" : "")
                       .replace("CONFIRMATION_CLOSE", representationDto.isCategoryConfirmation() ? "</b></u>" : "")
                       .replace("QUALIFICATION", representationDto.getQualification())
                       .replace("OVERALL_WORK_EXPERIENCE", representationDto.getOverallWorkExperience())
                       .replace("LAST_POS_WORK_EXPERIENCE", representationDto.getLastPositionWorkExperience());

            html = StringDocumentsUtils.replace(html, "COMPANY", representationDto.getPrincipalCompany());
            html = StringDocumentsUtils.replace(html, "RECOMMENDATION", representationDto.getRecommendation(), 70);
            html = StringDocumentsUtils.replace(html, "SHOWING", representationDto.getShowing(), 40);
            html = StringDocumentsUtils.replace(html, "FLAWS", representationDto.getFlaws(), 30);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(html.getBytes());
    }

}
