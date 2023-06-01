package com.wabs.website.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "patches")
public class Patch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "topic")
    @ManyToOne(fetch = FetchType.EAGER)
    private Topic topic;

    @Column(name = "patch")
    private BigDecimal patch;

    @Column(name = "title", length = 100)
    private String title;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @Column(name = "published")
    private boolean published;

    @OneToOne
    @JoinColumn(name = "image")
    private Image image;

    public void setAll(Topic topic, BigDecimal patch, String title,
                       String description, LocalDateTime releaseDate, boolean published, Image image) {
        this.topic = topic;
        this.patch = patch;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.published = published;
        this.image = image;
    }

}
