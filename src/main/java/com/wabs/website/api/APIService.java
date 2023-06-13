package com.wabs.website.api;

import com.wabs.website.api.exceptions.MatchNotFoundException;
import com.wabs.website.api.models.MatchModel;
import com.wabs.website.api.models.PlayerModel;
import com.wabs.website.api.exceptions.PlayerNotFoundException;
import com.wabs.website.models.Match;
import com.wabs.website.models.Player;
import com.wabs.website.repository.MatchRepository;
import com.wabs.website.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class APIService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    public PlayerModel getPlayerByUsername(String username) throws PlayerNotFoundException {
        return getPlayerModel(playerRepository.findByUsername(username));
    }

    public PlayerModel getPlayerById(Long id) throws PlayerNotFoundException {
        return getPlayerModel(playerRepository.findById(id));
    }

    private PlayerModel getPlayerModel(Optional<Player> player) throws PlayerNotFoundException {
        if (player.isEmpty()) {
            throw new PlayerNotFoundException();
        }

        return PlayerModel.playerToModel(player.get());
    }

    public List<MatchModel> getPlayerMatches(String username) throws PlayerNotFoundException {
        return getPlayerMatches(playerRepository.findByUsername(username));
    }

    public List<MatchModel> getPlayerMatches(Long id) throws PlayerNotFoundException {
        return getPlayerMatches(playerRepository.findById(id));
    }

    private List<MatchModel> getPlayerMatches(Optional<Player> optionalPlayer) throws PlayerNotFoundException {
        if (optionalPlayer.isPresent()) {
            List<Match> matches = matchRepository.findByPlayerId(optionalPlayer.get().getId());
            List<MatchModel> matchModels = new ArrayList<>();

            matches.forEach(match -> matchModels.add(MatchModel.matchToModel(match)));

            return matchModels;
        } else {
            throw new PlayerNotFoundException();
        }
    }

    public MatchModel getMatch(Long id) throws MatchNotFoundException {
        Optional<Match> match = matchRepository.findById(id);
        if (match.isPresent()) {
            return MatchModel.matchToModel(match.get());
        } else {
            throw new MatchNotFoundException();
        }
    }



}
