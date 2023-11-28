package com.ays.ms.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
@Data
public class Genres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Serie> serieGenre;

    @ManyToMany(mappedBy = "genres")
    private Set<Film> filmGenre;


}
