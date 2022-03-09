package com.hs.coursemanagerback.service.employee;

import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.service.user.PrincipleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@Service
public class EmployeeNoteService {

    private final JavaMailSender javaMailSender;
    private final PrincipleService principleService;
    private final Logger logger;

    @Autowired
    public EmployeeNoteService(JavaMailSender javaMailSender, PrincipleService principleService, Logger logger) {
        this.javaMailSender = javaMailSender;
        this.principleService = principleService;
        this.logger = logger;
    }

    public void process(Employee employee) {
        if (employee.getNotificationDate() != null && employee.getNotificationDate().isEqual(LocalDate.now())) {
            sendNotificationEmail(employee);
            extendDate(employee);
        }
    }

    private void extendDate(Employee employee) {
        if (employee.isShouldExtendNotification()) {
            employee.setNotificationDate(LocalDate.now().plusDays(1));
        }
    }

    private void sendNotificationEmail(Employee employee) {
        try {
            javaMailSender.send(generateMessage(employee, "NOTE"));
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("Unable to send an email!");
        }
    }

    private MimeMessage generateMessage(Employee employee, String type) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsg = "";

        if (type.equals("NOTE")) {
            htmlMsg = generateNote(employee);
        }

        htmlMsg = htmlMsg.replace("ID", "" + employee.getId());
        htmlMsg = htmlMsg.replace("FULL_NAME", employee.getFullName());

        helper.setText(htmlMsg, true);
        helper.setTo(employee.getUser().getEmail());
        helper.setSubject("Уведомление по сотруднику " + employee.getFullName());
        helper.setFrom("course.manager@bot.by");

        return mimeMessage;
    }

    private String generateNote(Employee employee) {
        String html = getForm();

        if (StringUtils.isNotBlank(employee.getNote())) {
            StringBuilder sb = new StringBuilder("<h5>Заметки:</h5>\n");

            for (String line : employee.getNote().split("\n")) {
                sb.append("<div>" + line + "</div>");
            }

            html = html.replace("TO_REPLACE", sb.toString());
        } else {
            html = html.replace("TO_REPLACE", "");
        }

        return html;
    }

    private String getForm() {
        String form = "";

        try {
            form = Files.readString(Path.of("src/main/java/com/hs/coursemanagerback/html-forms/notification.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return form;
    }

}