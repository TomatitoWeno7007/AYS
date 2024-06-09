package com.ays.ms.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class UserCurrentFilmId implements Serializable {

    private Long userId;
    private Long filmId;

    public UserCurrentFilmId(Long userId, Long filmId) {
        this.userId = userId;
        this.filmId = filmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCurrentFilmId that = (UserCurrentFilmId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(filmId, that.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, filmId);
    }
}