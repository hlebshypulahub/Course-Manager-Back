package com.hs.coursemanagerback.service.file;

import com.hs.coursemanagerback.exception.FileStorageException;
import com.hs.coursemanagerback.model.employee.dto.EmployeeFromClientDto;
import com.hs.coursemanagerback.service.employee.EmployeeDataService;
import com.hs.coursemanagerback.service.employee.EmployeeFileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileService {

    private static final String URL = "http://82.209.251.148:60080";
    private static final String FILELIST_URL = URL + "/filelist";
    @Value("${spring.servlet.multipart.location}")
    public String uploadDir;

    private final EmployeeFileService employeeFileService;
    private final EmployeeDataService employeeDataService;

    @Autowired
    public FileService(EmployeeDataService employeeDataService, EmployeeFileService employeeFileService) {
        this.employeeFileService = employeeFileService;
        this.employeeDataService = employeeDataService;
    }

    public void uploadFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                Path path = Paths.get(FileUtils.getTempDirectory().getAbsolutePath(), UUID.randomUUID().toString());
                String tmpdir = Files.createDirectories(path).toFile().getAbsolutePath();

                String orgName = file.getOriginalFilename();
                String filePath = tmpdir + File.separator + orgName;
                File dest = new File(filePath);
                file.transferTo(dest);

                employeeFileService.readAndFillData(filePath);
            } catch (Exception e) {
                e.printStackTrace();
                throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                        + ". Please try again!");
            }
        }
    }

    /// TODO info updates (like surname of position)
    public void processFiles() {
        List<String> fileNames = getFileNames();

        for (String fileName : fileNames) {
            String fileContent = getFileContent(fileName);
            EmployeeFromClientDto employeeFromClientDto = buildDtoFromFileContent(fileContent);
            employeeDataService.buildEmployeeFromEmployeeFromClientDto(employeeFromClientDto);
        }
    }

    /// TODO Notificate email if not valid
    /// Check if employee exists and update if so
    public EmployeeFromClientDto buildDtoFromFileContent(String fileContent) {
        if (fileContent != null && !fileContent.isBlank()) {
            String[] parts = fileContent.split(";");

            if (parts.length == 5) {
                EmployeeFromClientDto employeeFromClientDto = new EmployeeFromClientDto();
                employeeFromClientDto.setForeignId(Long.valueOf(parts[0]));
                employeeFromClientDto.setFullName(parts[1]);
                employeeFromClientDto.setHiringDate(parts[2]);
                employeeFromClientDto.setJobFacility(parts[3]);
                employeeFromClientDto.setPosition(parts[4]);

                return employeeFromClientDto;
            } else {
                /// email
            }
        }

        return null;
    }

    public String getFileContent(String fileName) {
        try (InputStream in = new URL(URL + "/" + fileName).openStream()) {
            byte[] bytes = in.readAllBytes();

            return
                    new BufferedReader(
                            new InputStreamReader(
                                    new ByteArrayInputStream(bytes),
                                    Charset.forName("Windows-1251")))
                            .readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String> getFileNames() {
        try (InputStream in = new URL(FILELIST_URL).openStream()) {
            byte[] bytes = in.readAllBytes();

            String fileString = new String(bytes, Charset.defaultCharset());

            return Arrays.stream(fileString.split("\\r?\\n")).filter(s -> s.startsWith("K") && s.endsWith(".csv")).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
