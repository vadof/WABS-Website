package com.wabs.website.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@Controller
public class DownloadController {

    @GetMapping("/download")
    public String home(Model model) {
        model.addAttribute("title", "Download");

        return "download";
    }

    @GetMapping("/download/jar")
    public void downloadFile(HttpServletResponse response) throws IOException {
        File file = new File("src/main/resources/files/wabs.jar");

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

}
