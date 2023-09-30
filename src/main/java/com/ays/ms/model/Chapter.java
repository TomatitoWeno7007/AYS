package com.ays.ms.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chapter")
@Data
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String name;
    private String synopsis;
    private long number;
    private String url;
    private String img;
}
