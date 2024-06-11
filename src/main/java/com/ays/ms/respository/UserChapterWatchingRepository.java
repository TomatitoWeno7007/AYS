package com.ays.ms.respository;

import com.ays.ms.model.*;
import com.ays.ms.respository.springdata.UserChapterWatchingDataRepository;
import com.ays.ms.respository.springdata.UserFilmWatchingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserChapterWatchingRepository {
    @Autowired
    private UserChapterWatchingDataRepository userChapterWatchingDataRepository;

    public UserChapterWatching getChapterWatching(UserChapterWatchingId id) {
        Optional<UserChapterWatching> optionalWatcher = this.userChapterWatchingDataRepository.findById(id);
        return optionalWatcher.orElse(null);
    }

    public void saveChapterWatching(UserChapterWatching userChapterWatching) {
        this.userChapterWatchingDataRepository.save(userChapterWatching);
    }

    public void deleteChapterWatching(UserChapterWatchingId id) { this.userChapterWatchingDataRepository.deleteById(id); }
}
