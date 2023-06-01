package com.wabs.website.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "faqs")
@NoArgsConstructor
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question")
    private String question;

    @Lob
    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

}
