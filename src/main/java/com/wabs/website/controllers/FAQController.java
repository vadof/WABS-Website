package com.wabs.website.controllers;

import com.wabs.website.repository.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FAQController {

    @Autowired
    private FAQRepository faqRepository;

    @GetMapping("/faqs")
    public String faqs(Model model) {
        model.addAttribute("title", "FAQs");
        model.addAttribute("faqs", faqRepository.findAll());
        return "faqs";
    }

}
