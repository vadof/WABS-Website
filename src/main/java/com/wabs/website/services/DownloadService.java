package com.wabs.website.services;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DownloadService {

    @Value("${game.file.path}")
    private String filePath;

    @Value("${game.file.name}")
    private String fileName;

    public void downloadFile(HttpServletResponse response) throws IOException {
        File file = new File(filePath + "/" + fileName);

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", file.getName());

        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        byte[] buffer = new byte[8192];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
    }

    public void updateFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                deleteOldGameFile();
                File dest = new File(filePath + "/" + fileName);
                file.transferTo(dest);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void deleteOldGameFile() {
        String currentFilePath = filePath + "/" + fileName;

        Path currentPath = Paths.get(currentFilePath);
        try {
            Files.delete(currentPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
