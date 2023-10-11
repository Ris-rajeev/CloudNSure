package com.realnet.HealthCheckup.Services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

@Component
public class FileUploadHelperService {

    @Value("${projectPath}")
    private String projectPath;

    public boolean uploadFile(MultipartFile multipartFile, String uploadDirectory) throws IOException {
        String dirPath = projectPath + File.separator + uploadDirectory;
        File directory = new File(dirPath);
        
        if (!directory.exists()) {
            boolean mkdirs = directory.mkdirs();
            System.out.println(dirPath + "  folder create =  " + mkdirs);
        }

        String filePath = dirPath + File.separator + multipartFile.getOriginalFilename();
        File file = new File(filePath);

        try (InputStream is = multipartFile.getInputStream();
             FileOutputStream fos = new FileOutputStream(file)) {

            byte[] data = new byte[is.available()];
            is.read(data);
            fos.write(data);

            // Set file permissions (Unix-based environment)
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
            Files.setPosixFilePermissions(file.toPath(), permissions);
        } catch (IOException e) {
            // Handle exception here or throw it back to the caller
            e.printStackTrace();
            return false;
        }

        System.out.println("File created successfully");
        System.out.println("File path: " + filePath);
        return true;
    }
}
