package com.wabs.website.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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

    @PrePersist
    public void setKd() {
        kd = BigDecimal.ZERO;
    }

}
