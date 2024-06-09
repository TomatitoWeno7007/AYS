//package com.ays.ms.model;
//
//import javax.persistence.*;
//import lombok.Data;
//
//import java.util.Objects;
//
//@Entity
//@Table(name = "userCurrentFilm")
//@Data
//public class UserCurrentFilm {
//
//    @EmbeddedId
//    private UserCurrentFilmId id;
//
//    @ManyToOne
//    @MapsId("userId")
//    @JoinColumn(name = "user_id")
//    private User currentUserId;
//
//    @ManyToOne
//    @MapsId("filmId")
//    @JoinColumn(name = "film_id")
//    private Film currentFilmId;
//
//    @Column(name = "current_time")
//    private String currentTime;
//
//    public UserCurrentFilm(User currentUserId, Film currentFilmId, String currentTime) {
//        this.currentUserId = currentUserId;
//        this.currentFilmId = currentFilmId;
//        this.currentTime = currentTime;
//        this.id = new UserCurrentFilmId(currentUserId.getId(), currentFilmId.getId());
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserCurrentFilm that = (UserCurrentFilm) o;
//        return Objects.equals(id, that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//}
