package com.wabs.website.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "player_match_statistics")
public class PlayerMatchStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "kills")
    private int kills;

    @Column(name = "damage_dealt")
    private int damageDealt;

    @Column(name = "deaths")
    private int deaths;

    @Column(name = "damage_received")
    private int damageReceived;

}
