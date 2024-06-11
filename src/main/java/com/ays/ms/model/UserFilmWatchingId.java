package com.ays.ms.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFilmWatchingId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "film_id")
    public Film film;

    public UserFilmWatchingId(){}
    public UserFilmWatchingId(User user, Film film) {
        this.user = user;
        this.film = film;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFilmWatchingId that = (UserFilmWatchingId) o;
        return Objects.equals(user, that.user) && Objects.equals(film, that.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, film);
    }
}