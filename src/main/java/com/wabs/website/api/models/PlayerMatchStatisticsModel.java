package com.wabs.website.api.models;

import com.wabs.website.models.PlayerMatchStatistics;
import lombok.Getter;

@Getter
public class PlayerMatchStatisticsModel {

    private final long playerId;

    private final int kills;

    private final int damageDealt;

    private final int deaths;

    private final int damageReceived;

    private PlayerMatchStatisticsModel(long playerId, int kills,
                                       int damageDealt, int deaths, int damageReceived) {
        this.playerId = playerId;
        this.kills = kills;
        this.damageDealt = damageDealt;
        this.deaths = deaths;
        this.damageReceived = damageReceived;
    }

    public static PlayerMatchStatisticsModel playerMatchStatisticsToModel(final PlayerMatchStatistics pms) {
        return new PlayerMatchStatisticsModel(pms.getPlayer().getId(), pms.getKills(),
                pms.getDamageDealt(), pms.getDeaths(), pms.getDamageReceived());
    }
}
