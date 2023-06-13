package com.wabs.website.controllers;

import com.wabs.website.models.*;
import com.wabs.website.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private PatchRepository patchRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Wave Attack");

        List<Patch> patches = patchRepository.getLastPublishedPatches().stream().limit(2)
                .collect(Collectors.toList());
        Collections.reverse(patches);

        model.addAttribute("patches", patches);
        return "index";
    }

    @PostMapping("/search")
    public String searchPlayer(@RequestParam String username) {
        if (username.length() > 0) {
            return "redirect:/" + username + "/statistics";
        }
        return "index";
    }

    @PostMapping("/email")
    public String saveEmail(@RequestParam String email) {
        if (emailRepository.searchByEmail(email) == null) {
            Email email1 = new Email();
            email1.setEmail(email);
            email1.setUpdates(true);
            emailRepository.save(email1);
        }
        return "index";
    }

}
