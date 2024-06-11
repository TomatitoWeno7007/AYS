package com.ays.ms.respository;

import com.ays.ms.model.Chapter;
import com.ays.ms.model.UserFilmWatching;
import com.ays.ms.model.UserFilmWatchingId;
import com.ays.ms.respository.springdata.UserFilmWatchingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserFilmWatchingRepository {
    @Autowired
    private UserFilmWatchingDataRepository userFilmWatchingDataRepository;

    public UserFilmWatching getFilmWatching(UserFilmWatchingId id) {
        Optional<UserFilmWatching> optionalWatcher = this.userFilmWatchingDataRepository.findById(id);
        return optionalWatcher.orElse(null);
    }

    public void saveFilmWatching(UserFilmWatching userFilmWatching) {
        this.userFilmWatchingDataRepository.save(userFilmWatching);
    }

    public void deleteFilmWatching(UserFilmWatchingId id) { this.userFilmWatchingDataRepository.deleteById(id); }
}
