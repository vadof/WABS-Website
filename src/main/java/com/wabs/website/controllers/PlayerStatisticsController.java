package com.wabs.website.controllers;

import com.wabs.website.models.Player;
import com.wabs.website.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PlayerStatisticsController {

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/{username}/statistics")
    public String statistics(@PathVariable(value = "username") String username, Model model) {
        Player player = playerRepository.findByUsername(username);

        if (player == null) {
            return "redirect:/";
        }

        model.addAttribute("title", username);
        model.addAttribute("player", player);

        return "statistics";
    }

}
