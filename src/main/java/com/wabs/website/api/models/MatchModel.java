package com.wabs.website.api.models;

import com.wabs.website.models.Match;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MatchModel {

    private final long id;

    private final boolean win;

    private final LocalDateTime date;

    private final List<PlayerMatchStatisticsModel> playerMatchStatistics;

    private MatchModel(long id, boolean win, LocalDateTime date,
                       List<PlayerMatchStatisticsModel> playerMatchStatistics) {
        this.id = id;
        this.win = win;
        this.date = date;
        this.playerMatchStatistics = playerMatchStatistics;
    }

    public static MatchModel matchToModel(final Match match) {
        List<PlayerMatchStatisticsModel> pmsModels = new ArrayList<>();
        match.getPlayerMatchStats().forEach(pms ->
                pmsModels.add(PlayerMatchStatisticsModel.playerMatchStatisticsToModel(pms)));

        return new MatchModel(match.getId(), match.isWin(),
                match.getDate(), pmsModels);
    }
}
