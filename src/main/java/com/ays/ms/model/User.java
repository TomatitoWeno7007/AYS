package com.ays.ms.model;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String lastName;
    private String secondLastName;
    private String email;
    private LocalDate dateBirth;
    private String password;
    private String img;
    private boolean admin;

    @OneToOne
    private Card card;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Serie> watchSeries;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Film> watchFilms;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Serie> likedSeries;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Film> likedFilms;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Serie> recommendedSeries;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Film> recommendedFilms;


}
