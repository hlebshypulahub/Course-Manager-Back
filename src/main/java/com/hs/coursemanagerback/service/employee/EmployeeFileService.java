package com.hs.coursemanagerback.service.employee;

import com.hs.coursemanagerback.model.employee.Employee;
import com.hs.coursemanagerback.model.user.User;
import com.hs.coursemanagerback.repository.EmployeeRepository;
import com.hs.coursemanagerback.service.user.PrincipleService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmployeeFileService {

    private final EmployeeRepository employeeRepository;
    private final Logger logger;
    private final PrincipleService principleService;

    @Autowired
    public EmployeeFileService(EmployeeRepository employeeRepository, PrincipleService principleService, Logger logger) {
        this.principleService = principleService;
        this.employeeRepository = employeeRepository;
        this.logger = logger;
    }

    public void readAndFillData(String filePath) {
        Path path = Paths.get(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {

            logger.info("Reading employees data file");

            String line = br.readLine();

            while (line != null) {
                String[] employeeAttributes = line.split(",");

                long foreignId = Long.parseLong(employeeAttributes[0]);
                if (employeeRepository.findByForeignId(foreignId).isEmpty()) {
                    Employee employee = new Employee();
                    employee.setForeignId(foreignId);
                    employee.setFullName(employeeAttributes[1]);
                    employee.setHiringDate(LocalDate.parse(employeeAttributes[2], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    employee.setJobFacility(employeeAttributes[3]);
                    employee.setPosition(employeeAttributes[4]);
                    employee.setExemptioned(false);
                    employee.setActive(true);

                    User user = principleService.getPrincipalUser();
                    user.addEmployee(employee);

                    employeeRepository.save(employee);

                    logger.info("Persisting employee " + employee.getForeignId() + " " + employee.getFullName());
                }

                line = br.readLine();
            }
        } catch (IOException ioe) {
            logger.error("Cannot read a file with employees data");
            ioe.printStackTrace();
        }
    }

}
