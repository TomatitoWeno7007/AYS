package com.ays.ms.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserChapterWatchingId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    public Chapter chapter;

    public UserChapterWatchingId(){}
    public UserChapterWatchingId(User user, Chapter chapter) {
        this.user = user;
        this.chapter = chapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChapterWatchingId that = (UserChapterWatchingId) o;
        return Objects.equals(user, that.user) && Objects.equals(chapter, that.chapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, chapter);
    }
}