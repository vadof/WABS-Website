package com.wabs.website.controllers;

import com.wabs.website.models.Match;
import com.wabs.website.models.Player;
import com.wabs.website.models.PlayerMatchStatistics;
import com.wabs.website.repository.MatchRepository;
import com.wabs.website.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class MatchController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    @GetMapping("/{username}/matches")
    public String playerMatches(@PathVariable(value = "username") String username, Model model) {
        Optional<Player> optionalPlayer = playerRepository.findByUsername(username);

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();

            List<Match> matchList = matchRepository.findByPlayerId(player.getId());
            List<PlayerMatchStatistics> playerMatchStatistics = new ArrayList<>();
            Collections.reverse(matchList);

            for (Match match: matchList) {
                for (PlayerMatchStatistics pms : match.getPlayerMatchStats()) {
                    if (pms.getPlayer().equals(player)) {
                        playerMatchStatistics.add(pms);
                    }
                }
            }

            model.addAttribute("title", player.getUsername());
            model.addAttribute("player", player);
            model.addAttribute("matchList", matchList);
            model.addAttribute("playerStats", playerMatchStatistics);

            return "player-matches";
        }

        return "redirect:/";
    }

    @GetMapping("/match/{id}/info")
    public String matchInfo(@PathVariable(value = "id") long id, Model model) {
        Optional<Match> match = matchRepository.findById(id);

        if (match.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("title", "Match #" + match.get().getId());
        model.addAttribute("match", match.get());

        return "match-info";
    }

}
