package com.wabs.website.controllers;

import com.wabs.website.models.Image;
import com.wabs.website.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(image.getContentType()));
            headers.setContentLength(image.getSize());
            return new ResponseEntity<>(image.getBytes(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
