package com.wabs.website.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email", unique = true)
    private String email;

    @OneToOne(mappedBy = "email")
    private Player playerId;

    @Column(name = "updates")
    private boolean updates;

    public Email(String email, boolean updates) {
        this.email = email;
        this.updates = updates;
    }

}
