package com.wabs.website.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "player_statistics")
    private PlayerStatistics playerStatistics;

    @OneToOne
    @JoinColumn(name = "player_email")
    private Email email;

    @Column(name = "registered")
    private LocalDate registered;

    public Player(String username, String password, Email email, PlayerStatistics playerStatistics) {
        this.username = username;
        this.password = password;
        this.playerStatistics = playerStatistics;
        this.email = email;
    }

    @PrePersist
    public void prePersist() {
        registered = LocalDate.now();
    }
}
