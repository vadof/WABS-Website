package com.wabs.website.controllers;

import com.wabs.website.models.Email;
import com.wabs.website.models.Player;
import com.wabs.website.models.PlayerStatistics;
import com.wabs.website.repository.EmailRepository;
import com.wabs.website.repository.PlayerRepository;
import com.wabs.website.repository.PlayerStatisticsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RegisterController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private PlayerStatisticsRepository playerStatisticsRepository;

    @GetMapping("/register")
    public String statistics(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("errorMessage", "null");
        return "register";
    }


    @PostMapping("/register")
    @Transactional
    public String registerPlayer(@RequestParam String username, @RequestParam String email,
                               @RequestParam String password, @RequestParam String passwordRepeat,
                               @RequestParam(name = "updates", required = false) boolean updates,
                               Model model) {

        if (username.length() > 0 && password.length() > 0
                && email.length() > 0 && passwordRepeat.length() > 0) {

            Email email1 = emailRepository.searchByEmail(email);

            if (playerRepository.findByUsername(username).isPresent()) {
                model.addAttribute("errorMessage", "Username already exists!");
                return "register";
            } else if (!password.equals(passwordRepeat)) {
                model.addAttribute("errorMessage", "Password mismatch!");
            } else if (email1 != null && email1.getPlayerId() != null) {
                model.addAttribute("errorMessage", "This email is already used!");
            } else {
                savePlayer(username, email, password, updates);

                return "index";
            }
        } else {
            model.addAttribute("errorMessage", "Fill in the empty fields!");
        }
        return "register";
    }

    private void savePlayer(String username, String email, String password, boolean updates) {
        PlayerStatistics playerStatistics = new PlayerStatistics();
        playerStatisticsRepository.save(playerStatistics);

        Email playerEmail = new Email(email, updates);
        emailRepository.save(playerEmail);

        Player player = new Player(username, password, playerEmail, playerStatistics);
        playerRepository.save(player);
    }

}
