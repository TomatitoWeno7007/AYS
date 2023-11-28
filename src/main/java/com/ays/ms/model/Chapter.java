package com.ays.ms.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "chapter")
@Data
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String name;
    protected String duration;
    private String synopsis;
    private long number;
    private String url;
    private String img;
}
