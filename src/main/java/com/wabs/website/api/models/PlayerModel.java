package com.wabs.website.api.models;

import com.wabs.website.models.Player;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PlayerModel {

    private final long id;

    private final String username;

    private final PlayerStatisticsModel playerStatistics;

    private final LocalDate registered;

    private PlayerModel(long id, String username, PlayerStatisticsModel playerStatistics, LocalDate registered) {
        this.id = id;
        this.username = username;
        this.playerStatistics = playerStatistics;
        this.registered = registered;
    }

    public static PlayerModel playerToModel(final Player player) {
        return new PlayerModel(player.getId(), player.getUsername(),
                PlayerStatisticsModel.playerStatisticsToModel(player.getPlayerStatistics()), player.getRegistered());
    }
}
