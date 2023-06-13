package com.wabs.website.api;

import com.wabs.website.api.exceptions.MatchNotFoundException;
import com.wabs.website.api.models.MatchModel;
import com.wabs.website.api.models.PlayerModel;
import com.wabs.website.api.exceptions.PlayerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private APIService apiService;

    @GetMapping("/players/username/{username}")
    public ResponseEntity<PlayerModel> getPlayer(@PathVariable String username) {
        try {
            PlayerModel playerModel = apiService.getPlayerByUsername(username);
            return ResponseEntity.ok(playerModel);
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/players/id/{id}")
    public ResponseEntity<PlayerModel> getPlayer(@PathVariable Long id) {
        try {
            PlayerModel playerModel = apiService.getPlayerById(id);
            return ResponseEntity.ok(playerModel);
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/matches/username/{username}")
    public ResponseEntity<List<MatchModel>> getPlayerMatches(@PathVariable String username) {
        try {
            List<MatchModel> matches = apiService.getPlayerMatches(username);
            return ResponseEntity.ok(matches);
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/matches/id/{id}")
    public ResponseEntity<List<MatchModel>> getPlayerMatches(@PathVariable Long id) {
        try {
            List<MatchModel> matches = apiService.getPlayerMatches(id);
            return ResponseEntity.ok(matches);
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<MatchModel> getMatch(@PathVariable Long id) {
        try {
            MatchModel match = apiService.getMatch(id);
            return ResponseEntity.ok(match);
        } catch (MatchNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
