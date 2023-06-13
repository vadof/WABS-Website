package com.wabs.website.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "win")
    private boolean win;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToMany(mappedBy = "match")
    private List<PlayerMatchStatistics> playerMatchStats;

    @PrePersist
    public void prePersist() {
        date = LocalDateTime.now();
    }

}
