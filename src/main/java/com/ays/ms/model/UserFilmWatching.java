package com.ays.ms.model;

import javax.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "user_film_watching")
@Data
public class UserFilmWatching {

    @EmbeddedId
    private UserFilmWatchingId id;

    @Column(name = "actual_time")
    private String actualTime;

    public UserFilmWatching() {
    }

    public UserFilmWatching(User currentUser, Film currentFilm, String actualTime) {
        this.actualTime = actualTime;
        this.id = new UserFilmWatchingId(currentUser, currentFilm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFilmWatching that = (UserFilmWatching) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
