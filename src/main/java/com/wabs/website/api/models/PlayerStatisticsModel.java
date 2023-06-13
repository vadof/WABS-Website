package com.wabs.website.api.models;

import com.wabs.website.models.PlayerStatistics;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PlayerStatisticsModel {

    private final int totalGames;

    private final int victories;

    private final int defeats;

    private final BigDecimal kd;

    private final int kills;

    private final int damageDealt;

    private final int deaths;

    private final int damageReceived;

    private PlayerStatisticsModel(int totalGames, int victories, int defeats,
                                 BigDecimal kd, int kills, int damageDealt, int deaths, int damageReceived) {
        this.totalGames = totalGames;
        this.victories = victories;
        this.defeats = defeats;
        this.kd = kd;
        this.kills = kills;
        this.damageDealt = damageDealt;
        this.deaths = deaths;
        this.damageReceived = damageReceived;
    }

    public static PlayerStatisticsModel playerStatisticsToModel(final PlayerStatistics ps) {
        return new PlayerStatisticsModel(ps.getTotalGames(), ps.getVictories(), ps.getDefeats(),
                ps.getKd(), ps.getKills(), ps.getDamageDealt(), ps.getDeaths(), ps.getDamageReceived());
    }
}
