package com.wabs.website.controllers;

import com.wabs.website.services.DownloadService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@Controller
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    @GetMapping("/download")
    public String home(Model model) {
        model.addAttribute("title", "Download");

        return "download";
    }

    @GetMapping("/download/jar")
    public void downloadFile(HttpServletResponse response) throws IOException {
        downloadService.downloadFile(response);
    }

}
