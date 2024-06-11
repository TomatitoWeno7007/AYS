package com.ays.ms.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "user_chapter_watching")
@Data
public class UserChapterWatching {

    @EmbeddedId
    private UserChapterWatchingId id;

    @Column(name = "actual_time")
    private String actualTime;

    public UserChapterWatching() {
    }

    public UserChapterWatching(User currentUser, Chapter currentChapter, String actualTime) {
        this.actualTime = actualTime;
        this.id = new UserChapterWatchingId(currentUser, currentChapter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChapterWatching that = (UserChapterWatching) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
