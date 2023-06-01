package com.wabs.website.controllers;

import com.wabs.website.models.*;
import com.wabs.website.services.AdminService;
import com.wabs.website.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Admin");

        return "admin/login";
    }

    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        model.addAttribute("title", "WABS Admin");
        model.addAttribute("total_players", adminService.getPlayerAmount());
        model.addAttribute("new_players", adminService.getPlayerAmountRegisteredToday());
        model.addAttribute("total_games", adminService.getTotalGamesPlayed());
        model.addAttribute("last_patch_date",
                adminService.getLastPatchDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        model.addAttribute("last_patch", adminService.getLastPatch());
        model.addAttribute("top_players", adminService.getTop4PlayersWithKd());

        return "admin/home";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/admin/patches")
    public String adminPatches(Model model) {
        model.addAttribute("title", "Admin Patches");
        model.addAttribute("patches", adminService.getAllPatchesInReversedOrder());

        return "admin/patches";
    }

    @GetMapping("/admin/patch/{id}")
    public String adminPatchView(@PathVariable Long id, Model model) {
        Optional<Patch> optionalPatch = adminService.getPatchById(id);

        if (optionalPatch.isPresent()) {
            Patch patch = optionalPatch.get();
            model.addAttribute("title", "Patch " + patch.getPatch());
            model.addAttribute("patch", patch);

            String date = patch.getReleaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            model.addAttribute("patch_date", date);

            return "admin/patch-view";
        }

        return "redirect:/";
    }

    @GetMapping("/admin/patch/add")
    public String patchAddPage(Model model) {
        model.addAttribute("title", "Add update");
        model.addAttribute("topics", adminService.getAllTopics());

        return "admin/patch-add";
    }

    @PostMapping("/admin/topic/add")
    public String addTopic(Topic topic) {
        adminService.saveTopic(topic);
        return "redirect:/admin/patch/add";
    }

    @PostMapping("/admin/patch/add")
    public String addPatch(@RequestParam Long topicId, @RequestParam BigDecimal patch, @RequestParam String title,
                           @RequestParam String description, @RequestParam String releaseDate,
                           @RequestParam("image") MultipartFile image) {
        adminService.savePatch(topicId, patch, title, description, releaseDate, image);
        return "redirect:/admin/patches";
    }

    @GetMapping("/admin/patch/{id}/edit")
    public String patchEdit(@PathVariable Long id, Model model) {
        Optional<Patch> optionalPatch = adminService.getPatchById(id);

        if (optionalPatch.isPresent()) {
            Patch patch = optionalPatch.get();
            model.addAttribute("title", "Patch " + patch.getPatch() + " edit");
            model.addAttribute("patch", patch);
            model.addAttribute("topics", adminService.getAllTopics());

            String date = patch.getReleaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"));
            model.addAttribute("date", date);

            return "admin/patch-edit";
        }

        return "redirect:/";
    }

    @PostMapping("/admin/patch/{id}/edit")
    public String editPatch(@PathVariable Long id, @RequestParam Long topicId, @RequestParam BigDecimal patch, @RequestParam String title,
                           @RequestParam String description, @RequestParam String releaseDate,
                            @RequestParam("image") MultipartFile image) {
        adminService.updatePatch(id, topicId, patch, title, description, releaseDate, image);
        return "redirect:/admin/patch/" + id;
    }

    @PostMapping("/admin/patch/{id}/delete")
    public String deletePatch(@PathVariable Long id) {
        adminService.deletePatch(id);
        return "redirect:/admin/patches";
    }

    @GetMapping("/admin/newsletter")
    public String sendNewsletterPage(Model model) {
        model.addAttribute("tittle", "Newsletter");

        return "admin/newsletter";
    }

    @PostMapping("/admin/message")
    public String sendNewsletter(@RequestParam String subject, @RequestParam String text,
                                 @RequestParam(required = false) boolean toSubscribers) {
        emailService.sendEmail(subject, text, toSubscribers);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/players")
    public String playersInfo(Model model) {
        model.addAttribute("title", "Players info");

        List<Player> players = adminService.getAllPlayers();
        Collections.reverse(players);
        model.addAttribute("players", players);

        return "admin/players-info";
    }

    @GetMapping("/admin/matches")
    public String matchesInfo(Model model) {
        model.addAttribute("title", "Matches info");

        List<Match> matches = adminService.getAllMatches();
        Collections.reverse(matches);
        model.addAttribute("matches", matches);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        model.addAttribute("formatter", formatter);

        return "admin/matches";
    }

    @GetMapping("/admin/game")
    public String game(Model model) {
        model.addAttribute("title", "Game update");
        return "admin/game";
    }

    @PostMapping("/admin/game/update")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        adminService.updateFile(file);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/faqs")
    public String faqs(Model model) {
        model.addAttribute("title", "FAQs");
        model.addAttribute("faqs", adminService.getAllFaqs());

        return "admin/faqs";
    }

    @GetMapping("/admin/faq/add")
    public String faqAddPage(Model model) {
        model.addAttribute("title", "FAQ add");

        return "admin/faq-add";
    }

    @PostMapping("/admin/faq/add")
    public String faqAdd(@RequestParam String question, @RequestParam String answer) {
        adminService.saveFAQ(question, answer);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/faq/{id}/edit")
    public String faqEdit(@PathVariable Long id, Model model) {
        Optional<FAQ> faq = adminService.getFAQById(id);

        if (faq.isPresent()) {
            model.addAttribute("title", "FAQ edit");
            model.addAttribute("faq", faq.get());

            return "admin/faq-edit";
        }

        return "redirect:/admin/home";
    }

    @PostMapping("/admin/faq/{id}/edit")
    public String faqEdit(@PathVariable Long id, @RequestParam String question, @RequestParam String answer) {
        adminService.editFaq(id, question, answer);
        return "redirect:/admin/faqs";
    }

    @PostMapping("/admin/faq/{id}/delete")
    public String faqDelete(@PathVariable Long id) {
        adminService.deleteFAQ(id);
        return "redirect:/admin/faqs";
    }

}
