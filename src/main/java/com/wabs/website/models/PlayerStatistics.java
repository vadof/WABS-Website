package com.wabs.website.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Setter
@Table(name = "players_statistics")
public class PlayerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(mappedBy = "playerStatistics")
    private Player playerId;

    @Column(name = "total_games")
    private int totalGames;

    @Column(name = "victories")
    private int victories;

    @Column(name = "defeats")
    private int defeats;

    @Column(name = "kd")
    private BigDecimal kd;

    @Column(name = "kills")
    private int kills;

    @Column(name = "damage_dealt")
    private int damageDealt;

    @Column(name = "deaths")
    private int deaths;

    @Column(name = "damage_received")
    private int damageReceived;

    public void addGame(boolean win) {
        totalGames++;
        if (win) {
            victories++;
        } else {
            defeats++;
        }
    }

    public void addKills(int kills) {
        this.kills += kills;
    }

    public void addDamageDealt(int damage) {
        this.damageDealt += damage;
    }

    public void addDamageReceived(int damage) {
        this.damageReceived += damage;
    }

    public void addDeaths(int deaths) {
        this.deaths += deaths;
    }

    public void refreshKd() {
        if (deaths == 0) {
            kd = new BigDecimal(kills).divide(new BigDecimal(1), 2, RoundingMode.HALF_UP);
        } else {
            kd = new BigDecimal(kills).divide(new BigDecimal(deaths), 2, RoundingMode.HALF_UP);
        }
    }

    @PrePersist
    public void setKd() {
        kd = BigDecimal.ZERO;
    }

}
