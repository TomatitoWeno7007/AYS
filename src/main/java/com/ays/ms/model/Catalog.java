package com.ays.ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "catalog")
@Data
public class Catalog {

    @Id
    private long id;

    @OneToMany
    private List<Serie> watchSeries;
    @OneToMany
    private List<Film> watchFilms;
    @OneToMany
    private List<Serie> likedSeries;
    @OneToMany
    private List<Film> likedFilms;

}
