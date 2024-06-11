package com.ays.ms.respository.springdata;

import com.ays.ms.model.UserFilmWatching;
import com.ays.ms.model.UserFilmWatchingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFilmWatchingDataRepository extends JpaRepository<UserFilmWatching, UserFilmWatchingId> {
}
