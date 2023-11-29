package com.ays.ms.model;

import javax.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "film")
@Data
public class Film extends Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;
    private String duration;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genres> genres;

    @ManyToMany( mappedBy = "likedFilms")
    private Set<User> usersLiked;

    @ManyToMany(mappedBy = "recommendedFilms")
    private Set<User> usersRecommended;

    @ManyToMany(mappedBy = "watchFilms")
    private Set<User> usersWatched;

}
