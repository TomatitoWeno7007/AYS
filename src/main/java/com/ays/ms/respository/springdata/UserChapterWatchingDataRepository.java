package com.ays.ms.respository.springdata;

import com.ays.ms.model.UserChapterWatching;
import com.ays.ms.model.UserChapterWatchingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChapterWatchingDataRepository extends JpaRepository<UserChapterWatching, UserChapterWatchingId> {
}
