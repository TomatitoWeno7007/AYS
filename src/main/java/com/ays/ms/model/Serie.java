package com.ays.ms.model;

import javax.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "serie")
@Data
public class Serie extends Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Season> seasons;

    @ManyToMany( mappedBy = "likedSeries")
    private Set<User> usersLiked;

    @ManyToMany(mappedBy = "recommendedSeries")
    private Set<User> usersRecommended;

    @ManyToMany(mappedBy = "watchSeries")
    private Set<User> usersWatched;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "serie_genre",
            joinColumns = @JoinColumn(name = "serie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genres> genres;

}
